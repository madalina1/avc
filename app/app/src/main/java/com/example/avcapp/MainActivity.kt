package com.example.avcapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.avcapp.utils.makeStatusBarTransparent
import com.google.android.material.tabs.TabLayout

/**
 * Main entry point into our app. This app follows the single-activity pattern, and all
 * functionality is implemented in the form of fragments.
 */
class MainActivity : AppCompatActivity() {

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeStatusBarTransparent()

        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById<ViewPager>(R.id.viewPager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText(R.string.testing_fragment))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(R.string.map_fragment))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(R.string.information_fragment))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = MyAdapter(this, supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        val sharedPref = this.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(getString(R.string.user_name), "John Doe")
            putString(getString(R.string.country), "Iasi, Romania")
            putString(getString(R.string.contact_name), "Robert Jr.")
            putString(getString(R.string.contact_number), "+40751753645")
            commit()
        }

        (this.findViewById<View>(R.id.welcomeMessage) as TextView).text = getString(R.string.welcome_message,
            sharedPref.getString(getString(R.string.user_name), ""))
    }

    fun goToSettings(view: View) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun goToOtherTest(view: View?) {
        val intent = Intent(this, OtherTesting::class.java)
        startActivity(intent)
    }

    fun goToSelfTest(view: View) {
        val intent = Intent(this, SelfTesting::class.java)
        startActivity(intent)
    }
}
