package com.example.avcapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import org.json.JSONArray
import org.json.JSONException
import java.io.*
import java.nio.charset.StandardCharsets


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val INITIAL_PERMS = arrayOf<String>(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_CONTACTS
    )

    private val INITIAL_REQUEST = 1337

    private var mMapView: MapView? = null
    private var googleMap: GoogleMap? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private val options = MarkerOptions()
    private val latlngs: ArrayList<Pair<LatLng, String>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun MapFragment() {
        // Required empty public constructor
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(
                activity!!.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            requestPermissions(INITIAL_PERMS, INITIAL_REQUEST)
        }

        val rootView = inflater.inflate(R.layout.fragment_map, container, false) as View
        mMapView = rootView.findViewById(R.id.mapView)
        mMapView!!.onCreate(savedInstanceState)
        options.infoWindowAnchor(0.0f, 0.0f)
        val inputStream: InputStream = resources.openRawResource(R.raw.hospital_locations)
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
            LocationServices.getFusedLocationProviderClient(activity!!)
        mMapView!!.onResume() // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mMapView!!.getMapAsync(OnMapReadyCallback { mMap ->
            googleMap = mMap
            // For showing a move to my location button
            googleMap!!.isMyLocationEnabled = true
            for ((first, second) in latlngs) {
                options.position(first)
                options.title(second)
                options.snippet("Institut capabil de tratare AVC")
                googleMap!!.addMarker(options)
            }
            val locationResult: Task<Location> =
                mFusedLocationProviderClient!!.lastLocation
            locationResult.addOnCompleteListener(
                activity!!
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
                        googleMap!!.setOnInfoWindowClickListener { marker ->
                            //Uri gmmIntentUri = Uri.parse("google.navigation:q="+marker.getPosition().latitude+","+marker.getPosition().longitude);
                            val gmmIntentUri: Uri =
                                Uri.parse("geo:0,0?q=" + marker.position.latitude + "," + marker.position.longitude + "(" + marker.title + ")")
                            val intent =
                                Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            intent.setPackage("com.google.android.apps.maps")
                            startActivity(intent)
                        }
                    }
                }
            }
        })
        return rootView
    }

    override fun onResume() {
        super.onResume()
        mMapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView!!.onLowMemory()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
