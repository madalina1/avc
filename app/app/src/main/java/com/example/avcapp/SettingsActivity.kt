package com.example.avcapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.avcapp.utils.makeStatusBarTransparent
import org.w3c.dom.Text


class SettingsActivity : AppCompatActivity() {

    private var listProfileCard: ArrayList<ProfileCard> = ArrayList<ProfileCard>()
    private var emergencyContactsList: ArrayList<EmergencyContactsCard> = ArrayList<EmergencyContactsCard>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        makeStatusBarTransparent()

        val sharedPref = this.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE) ?: return

        val userName = sharedPref.getString(getString(R.string.user_name), "")
        val country = sharedPref.getString(getString(R.string.country), "")
        val contactName = sharedPref.getString(getString(R.string.contact_name), "")
        val contactNumber = sharedPref.getString(getString(R.string.contact_number), "") as String

        val profilePlaceholder = this.findViewById(R.id.profile_placeholder) as TextView
        profilePlaceholder.text = userName?.let { getInitials(it) }

        val layout: View  = inflate(applicationContext, R.layout.cardview_emergency_contacts,null);

        val contactPlaceholder = layout.findViewById(R.id.contact_placeholder) as TextView
        contactPlaceholder.text = contactName?.let { getInitials(it) }

        val profileName = this.findViewById(R.id.profile_name) as TextView
        profileName.text = userName

        val profileLocation = this.findViewById(R.id.profile_city) as TextView
        profileLocation.text = country

        listProfileCard.add(
            ProfileCard(
                "Age",
                "40",
                R.drawable.ic_person_34dp
            )
        )

        listProfileCard.add(
            ProfileCard(
                "Age2",
                "40",
                R.drawable.ic_person_34dp
            )
        )

        listProfileCard.add(
            ProfileCard(
                "Age3",
                "40",
                R.drawable.ic_person_34dp
            )
        )

        listProfileCard.add(
            ProfileCard(
                "Age4",
                "40",
                R.drawable.ic_person_34dp
            )
        )

        emergencyContactsList.add(
            EmergencyContactsCard(
                R.color.colorPrimary,
                contactName,
                contactNumber
            )
        )

        val profileRecyclerView = findViewById<View>(R.id.profile_recycler_view) as RecyclerView
        val profileAdapter = ProfileRecyclerViewAdapter(this, listProfileCard)
        profileRecyclerView.layoutManager = GridLayoutManager(this, 3)
        profileRecyclerView.adapter = profileAdapter

        val contactsRecyclerView = findViewById<View>(R.id.emergency_contacts_recycler_view) as RecyclerView
        val contactsAdapter = EmergencyContactsRecyclerView(this, emergencyContactsList)
        contactsRecyclerView.layoutManager = GridLayoutManager(this, 1)
        contactsRecyclerView.adapter = contactsAdapter

    }

    private fun getInitials(name: String): String {
        return name
            .split(' ')
            .mapNotNull { it.firstOrNull()?.toString() }
            .reduce { acc, s -> acc + s }
    }

    fun backButton(view: View) {
        this.finish()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}