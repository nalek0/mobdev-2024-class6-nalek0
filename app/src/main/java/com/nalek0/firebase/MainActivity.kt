package com.nalek0.firebase

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)

        val signInButton: Button = findViewById(R.id.sign_in)
        signInButton.setOnClickListener { signIn() }
    }

    public override fun onStart() {
        super.onStart()

        updateUI(Firebase.auth.currentUser)
    }

    private fun signIn() {
        val emailWidget = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val passwordWidget = findViewById<EditText>(R.id.editTextTextPassword)
        val email = emailWidget.text.toString()
        val password = passwordWidget.text.toString()

        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = Firebase.auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user == null) {
            findViewById<TextView>(R.id.logTextView).text = "Not login"
        } else {
            findViewById<TextView>(R.id.logTextView).text = "email = ${user.email}, displayName = ${user.displayName}"
        }
    }

}
