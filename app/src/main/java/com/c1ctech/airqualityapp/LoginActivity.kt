package com.c1ctech.airqualityapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.c1ctech.airqualityapp.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {

    private val TAG = LoginActivity::class.qualifiedName

    lateinit var activityLoginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)




        activityLoginBinding.btnLogin.setOnClickListener {
            var email = activityLoginBinding.edtEmail.text
            var password = activityLoginBinding.edtPassword.text
            if ((email!!.isEmpty() && password!!.isEmpty()) || email!!.isEmpty() || password!!.isEmpty()) {
                Toast.makeText(
                    this, "Please enter email and password.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                activityLoginBinding.progressBar.visibility = View.VISIBLE
                // sign in existing user
                signIn(email.toString(), password.toString())
            }
        }

        activityLoginBinding.tvCreateNewAccount.setOnClickListener {
            //launch LoginActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    private fun signIn(email: String, password: String) {
        Utilities.mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        activityLoginBinding.progressBar.visibility = View.GONE
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user: FirebaseUser? = Utilities.mAuth.getCurrentUser()

                        updateUI(user)

                    } else {
                        activityLoginBinding.progressBar.visibility = View.GONE
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            this, "Please enter correct email and password..",
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

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = user.uid
            //  launch HomeActivity
            val intent = Intent(this, MapsActivity::class.java)
            //pass data to HomeActivity
            intent.putExtra("Email", email)
            startActivity(intent)
            finish()

        }
    }
}