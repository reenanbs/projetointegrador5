package android.biker.com.myapplication

import android.biker.com.myapplication.R.string.login
import android.biker.com.myapplication.R.string.senha
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit  var progressBar: ProgressBar
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = progressBarLogin
        auth= FirebaseAuth.getInstance()

        /*
        btLogin.setOnClickListener{
            var status =
                if (edUsuario.text.toString().equals("Biker")
                    &&edSenha.text.toString().equals("123"))
                    "login realizado com sucesso"

                else
                    "Usuário ou Senha inválidos"
            Toast.makeText(this,status,Toast.LENGTH_LONG).show()

            if(status.equals("login realizado com sucesso")){
                startActivity(Intent(Intent(this@MainActivity,MenuActivity::class.java)))
            }
        }
        */

        val user = auth.currentUser
        if (user != null) {
            // User is signed in
            Log.d("FirebaseLogin", "onAuthStateChanged:signed_in:" + user.uid)
            if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("ManterConectado", true)) {
                startActivity(Intent(this, MenuActivity::class.java))
                finish()
            }
        } else {
            // User is signed out
            Log.d("FirebaseLogin", "onAuthStateChanged:signed_out")
        }
    }
    fun forgotPassword(view:View){
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
        val password:String = edUsuario.text.toString()

        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
            progressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(user,password)
                    .addOnCompleteListener(this){
                        task ->
                        if(task.isComplete){
                            if(cbConectado.isChecked) {
                                PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("ManterConectado", true).commit()
                                PreferenceManager.getDefaultSharedPreferences(this).edit().putString("UserUID", task.getResult().user.uid).commit()
                                PreferenceManager.getDefaultSharedPreferences(this).edit().putString("UserToken", FirebaseInstanceId.getInstance().token).commit()
                            }
                            startActivity(Intent(this,MapsActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this,"Usuário inválido forneça email e senha válida", Toast.LENGTH_LONG).show()

                        }
                    }
        }

    }



}
