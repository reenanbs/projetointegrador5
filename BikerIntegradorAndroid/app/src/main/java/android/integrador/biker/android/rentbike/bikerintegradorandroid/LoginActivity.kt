package android.integrador.biker.android.rentbike.bikerintegradorandroid

import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit  var progressBar: ProgressBar
    lateinit var auth: FirebaseAuth
    lateinit var authListener: FirebaseAuth.AuthStateListener
    val key = "MANTERCONECTADO"
    var br = ConnReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        registerReceiver(br, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        progressBar = progressBarLogin
        auth= FirebaseAuth.getInstance()
        val user = auth.currentUser


        authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {

                Log.d("FirebaseLogin", "user logado" + user.uid)
                if(getSP() != "NOTHING") {
                    startActivity(Intent(this@LoginActivity, MenuActivity::class.java))
                    finish()
                }
            } else {

                Log.d("FirebaseLogin", "deslogado")
            }

        }

    }

    fun forgotPassword(view: View){
        startActivity(Intent(this,ForgotPasswordActivity::class.java))
    }

    fun registerUser(view:View){
        startActivity(Intent(this,RegisterActivity::class.java))
    }

    fun login(view:View){
        loginUser()
    }

    fun loginUser(){
        val user:String = edUsuario.text.toString()
        val password:String = edSenha.text.toString()

        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
            progressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(user,password)
                    .addOnCompleteListener(this){
                        task ->
                        if(task.isComplete && task.isSuccessful){
                            if(cbConectado.isChecked) {
                                saveSP("Sim")
                            }
                            startActivity(Intent(this,MenuActivity::class.java))
                            finish()
                        }
                        else{
                            Log.w("Login","falha",task.exception)
                            Toast.makeText(this,"Usuário inválido forneça email e senha válida", Toast.LENGTH_LONG).show()

                        }
                    }
        } else{
            Toast.makeText(this,"Usuário ou senha em branco", Toast.LENGTH_LONG).show()
        }

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

    override fun onStart() {
        super.onStart()
        auth?.addAuthStateListener(authListener!!)
    }

    override fun onStop() {
        super.onStop()
        if (authListener != null) {
            auth?.removeAuthStateListener(authListener!!);
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(br)
    }

}

