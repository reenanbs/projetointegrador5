package android.biker.com.myapplication

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_main.*

/**
 * Created by João Paulo on 27/03/2018.
 */

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()!!.hide()
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)
        auth= FirebaseAuth.getInstance()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.menu1 -> {
                Toast.makeText(this, "menu1", Toast.LENGTH_LONG).show()
            }
            R.id.menu2 -> {
                startActivity(Intent(Intent(this@MenuActivity,HistoricActivity::class.java)))
            }
            R.id.menu3 -> {
                auth.signOut()
                finish()
                PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("ManterConectado", false).commit()
                PreferenceManager.getDefaultSharedPreferences(this).edit().putString("UserToken", "").commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}