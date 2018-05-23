package android.biker.com.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var dbReference: DatabaseReference
    lateinit  var progressBar:ProgressBar
    lateinit var database:FirebaseDatabase
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()!!.hide()
        setContentView(R.layout.activity_register)
        progressBar = progressBarRegister
        database = FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()
        dbReference = database.reference.child("User")
    }

    fun register(view: View){
        createNewAccount()
    }

    fun createNewAccount(){
        val email:String = editText5.text.toString()
        val password:String = editText6.text.toString()

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            progressBar.visibility = View.VISIBLE

            auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){
                        task ->
                        if(task.isComplete){
                            val user: FirebaseUser? = auth.currentUser
                            verifyEmail(user)

                            val userBD = dbReference.child(user?.uid)
                            action()
                        }else{
                            Toast.makeText(this, "Falha ao Cadastrar",
                                    Toast.LENGTH_SHORT).show()
                            Log.w("FirebaseCadastro", "failed", task.exception)
                        }
                    }
        }
    }
    fun action(){
        startActivity(Intent(this,MainActivity::class.java))
    }

    fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
                ?.addOnCompleteListener(this){
                    task ->
                    if(task.isComplete){
                        Toast.makeText(this,"Email enviado", Toast.LENGTH_LONG).show()
                    } else{
                        Toast.makeText(this,"Erro ao enviar email", Toast.LENGTH_LONG).show()
                    }
                }
    }

}
