package com.example.avcapp.utils

import android.content.Intent
import android.location.Location
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import com.example.avcapp.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import org.json.JSONArray
import org.json.JSONException
import java.io.*
import java.nio.charset.StandardCharsets

fun createMap(mMapView: MapView?, fragmentActivity: FragmentActivity, flag: Boolean) {
    var googleMap: GoogleMap? = null
    var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    val options = MarkerOptions()
    val latlngs: ArrayList<Pair<LatLng, String>> = ArrayList()

    options.infoWindowAnchor(0.0f, 0.0f)
    val inputStream: InputStream = fragmentActivity.resources.openRawResource(R.raw.hospital_locations)
    val writer: Writer = StringWriter()
    val buffer = CharArray(1024)
    try {
        val reader: Reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
        var n: Int
        while (reader.read(buffer).also { n = it } != -1) {
            writer.write(buffer, 0, n)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    val jsonString: String = writer.toString()
    try {
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val explrObject = jsonArray.getJSONObject(i)
            val latitude = explrObject.getDouble("latitude")
            val longitude = explrObject.getDouble("longitude")
            val hospitalName = explrObject.getString("hospitalName")
            latlngs.add(Pair(LatLng(latitude, longitude), hospitalName))
        }
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    // Construct a FusedLocationProviderClient.
    mFusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(fragmentActivity)
    mMapView!!.onResume() // needed to get the map to display immediately
    try {
        MapsInitializer.initialize(fragmentActivity.applicationContext)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    mMapView.getMapAsync(OnMapReadyCallback { mMap ->
        googleMap = mMap
        // For showing a move to my location button
        googleMap!!.isMyLocationEnabled = true
        for ((first, second) in latlngs) {
            options.position(first)
            options.title(second)
            options.snippet(fragmentActivity.resources.getString(R.string.map_marker_snippet))
            googleMap!!.addMarker(options)
        }
        val locationResult: Task<Location> =
            mFusedLocationProviderClient!!.lastLocation
        locationResult.addOnCompleteListener(
            fragmentActivity
        ) { task ->
            if (task.isSuccessful) {
                val mLastKnownLocation: Location? = task.result
                if (mLastKnownLocation != null) {
                    val builder = LatLngBounds.Builder()
                    for ((first) in latlngs) {
                        builder.include(first)
                    }
                    builder.include(
                        LatLng(
                            mLastKnownLocation.latitude,
                            mLastKnownLocation.longitude
                        )
                    )
                    val bounds = builder.build()
                    val padding = 80 // offset from edges of the map in pixels
                    val cameraPosition =
                        CameraPosition.Builder().target(
                            LatLng(
                                mLastKnownLocation.latitude,
                                mLastKnownLocation.longitude
                            )
                        ).zoom(14f).build()
                    googleMap!!.animateCamera(
                        CameraUpdateFactory.newCameraPosition(
                            cameraPosition
                        )
                    )
                    googleMap!!.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            bounds,
                            padding
                        )
                    )
                    if(flag) {
                        googleMap!!.setOnInfoWindowClickListener { marker ->
                            //Uri gmmIntentUri = Uri.parse("google.navigation:q="+marker.getPosition().latitude+","+marker.getPosition().longitude);
                            val gmmIntentUri: Uri =
                                Uri.parse("geo:0,0?q=" + marker.position.latitude + "," + marker.position.longitude + "(" + marker.title + ")")
                            val intent =
                                Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            intent.setPackage("com.google.android.apps.maps")
                            fragmentActivity.startActivity(intent)
                        }
                    }
                    else {
                        googleMap!!.setOnMapClickListener(GoogleMap.OnMapClickListener {
                                latlngs.sortWith(Comparator { a, b ->
                                    val locationA = Location("point A")
                                    locationA.latitude = a.first.latitude
                                    locationA.longitude = a.first.longitude
                                    val locationB = Location("point B")
                                    locationB.latitude = b.first.latitude
                                    locationB.longitude = b.first.longitude
                                    val distanceOne: Float =
                                        mLastKnownLocation.distanceTo(locationA)
                                    val distanceTwo: Float =
                                        mLastKnownLocation.distanceTo(locationB)
                                    distanceOne.compareTo(distanceTwo)
                                })
                                val (first, second) = latlngs[0]
                                val gmmIntentUri: Uri =
                                    Uri.parse("geo:0,0?q=" + first.latitude.toString() + "," + first.longitude.toString() + "(" + second + ")")
                                val intent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                intent.setPackage("com.google.android.apps.maps")
                                fragmentActivity.startActivity(intent)
                            })
                        }
                }
            }
        }
    })
}