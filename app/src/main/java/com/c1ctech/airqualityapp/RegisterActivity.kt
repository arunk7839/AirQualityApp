package com.c1ctech.airqualityapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.c1ctech.airqualityapp.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterActivity : AppCompatActivity() {

    lateinit var  activityRegisterBinding: ActivityRegisterBinding

    private val TAG = LoginActivity::class.qualifiedName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)

        supportActionBar?.setTitle(R.string.create_an_account)


        //creating FirebaseAuth instance
        Utilities.mAuth = FirebaseAuth.getInstance()

        //get current user
        val currentUser: FirebaseUser? = Utilities.mAuth.currentUser
        if(currentUser!=null){
            //launch HomeActivity
            val intent = Intent(this, MapsActivity::class.java)
            //pass data to HomeActivity
            intent.putExtra("Email",currentUser.email)
            startActivity(intent)
            finish()

        }

        activityRegisterBinding.btnCreateAccount.setOnClickListener {
            var name = activityRegisterBinding.edtName.text
            var email = activityRegisterBinding.edtEmail.text
            var password = activityRegisterBinding.edtPassword.text

            if ((email!!.isEmpty() && password!!.isEmpty()) || email!!.isEmpty() || password!!.isEmpty() || name!!.isEmpty()) {
                Toast.makeText(
                    this, "Please enter name,email and password.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                activityRegisterBinding.progressBar.visibility = View.VISIBLE

                //sign up new user
                createAccount(email.toString(), password.toString())
            }

        }

        activityRegisterBinding.tvAlreadyHaveAccount.setOnClickListener {
            //launch LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun createAccount(email: String, password: String) {

        Utilities.mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        activityRegisterBinding.progressBar.visibility = View.GONE
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user: FirebaseUser? = Utilities.mAuth.getCurrentUser()
                        updateUI(user)
                    } else {
                        activityRegisterBinding.progressBar.visibility = View.GONE
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }

                    // ...
                })
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            //val name = user.displayName
            val email = user.email

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = user.uid

            //launch HomeActivity
            val intent = Intent(this, MapsActivity::class.java)
            //pass data to HomeActivity
            intent.putExtra("Email", user?.email)
            startActivity(intent)
            finish()


        }
    }
}