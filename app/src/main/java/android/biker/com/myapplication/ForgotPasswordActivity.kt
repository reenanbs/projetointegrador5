package android.biker.com.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
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
        getSupportActionBar()!!.hide()
        setContentView(R.layout.activity_forgot_password)
        progressBar = progressForgot
        auth= FirebaseAuth.getInstance()
    }

    fun send(view : View){
        val email:String = editEmail.text.toString()

        if(!TextUtils.isEmpty(email)){
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(this){
                        task ->
                        if(task.isSuccessful){
                            progressBar.visibility = View.VISIBLE
                            startActivity(Intent(this,MainActivity::class.java))
                        } else{
                            Toast.makeText(this,"Erro ao enviar email", Toast.LENGTH_LONG).show()
                        }
                    }
        }
    }
}
