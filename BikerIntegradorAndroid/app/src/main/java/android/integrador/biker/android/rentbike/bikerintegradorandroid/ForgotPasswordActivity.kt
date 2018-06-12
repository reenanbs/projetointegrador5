package android.integrador.biker.android.rentbike.bikerintegradorandroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*


class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit  var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        progressBar = progressForgot
        auth= FirebaseAuth.getInstance()
    }

    fun send(view : View){
        val email:String = editText.text.toString()

        if(!TextUtils.isEmpty(email)){
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(this){
                        task ->
                        if(task.isSuccessful){
                            progressBar.visibility = View.VISIBLE
                            startActivity(Intent(this,LoginActivity::class.java))
                        } else{
                            Log.w("Enviar","Erro",task.exception)
                            Toast.makeText(this,"Erro ao enviar email", Toast.LENGTH_LONG).show()
                        }
                    }

        }
    }
}
