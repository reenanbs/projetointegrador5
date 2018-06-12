package android.integrador.biker.android.rentbike.bikerintegradorandroid

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*
import kotlinx.android.synthetic.main.content_menu.*

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var auth: FirebaseAuth
    val key = "MANTERCONECTADO"
    private var user = FirebaseAuth.getInstance().currentUser
    lateinit var authListener: FirebaseAuth.AuthStateListener
    lateinit var dbReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)
        dbReference = FirebaseDatabase.getInstance().getReference()
        auth= FirebaseAuth.getInstance()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            user = firebaseAuth.currentUser
            if (user != null) {
                Toast.makeText(this@MenuActivity, "Conectado: " + user?.email, Toast.LENGTH_LONG).show()
            } else {

                Toast.makeText(this@MenuActivity, "Desconectado", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@MenuActivity, LoginActivity::class.java))
                finish()
            }

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
        menuInflater.inflate(R.menu.menu, menu)
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
                startActivity(Intent(Intent(this@MenuActivity,MapsActivity::class.java)))
            }
            R.id.menu2 -> {
                startActivity(Intent(Intent(this@MenuActivity,HistoricActivity::class.java)))
            }
            R.id.menu3 -> {
                auth.signOut()
                if(getSP() != "NOTHING"){
                    saveSP("NOTHING")
                }
                finish()

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun saveSP(dado: String){

        var pref = PreferenceManager.getDefaultSharedPreferences(this)
        var editor = pref.edit()

        //save infos
        editor.putString(key, dado)
        editor.commit()

        Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()

    }

    fun getSP(): String {

        var pref = PreferenceManager.getDefaultSharedPreferences(this)
        var value = pref.getString(key, "NOTHING")

        return value;

    }

    fun registerBike(view: View){

        val addValueEventListener = dbReference?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var idBike = dataSnapshot.child("bikes").childrenCount
                idBike += 1

                var bike = Bike(
                        idBike.toString(),
                        modelo.text.toString(),
                        ano.text.toString(),
                        tipo.text.toString(),
                        user?.uid.toString()
                )
                dbReference?.child("bikes")?.child(bike.id)?.setValue(bike)
                onBackPressed()

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

}
