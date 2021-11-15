package com.example.listpoke.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listpoke.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private var RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btn_google = findViewById<Button>(R.id.btn_google)
        val btn_cadastrar = findViewById<Button>(R.id.btn_cadastrar)
        val btn_entrar = findViewById<Button>(R.id.btn_entrar)

        var email1 = findViewById<EditText>(R.id.edt_email)
        var senha1 = findViewById<EditText>(R.id.edt_senha)

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("561088645883-4gse7a3dn08oge36c3v8es7jp4f79ee0.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

        btn_google.setOnClickListener { signIn() }

        btn_cadastrar.setOnClickListener {
            val cadastro = Intent(this, CadastroActivitiy::class.java)
            startActivity(cadastro)
        }

        btn_entrar.setOnClickListener {

            var email = email1.text.toString().trim()
            var senha = senha1.text.toString().trim()

            auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        val ini = Intent(this, PokemonListActivity::class.java)
                        startActivity(ini)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("E", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Erro na autenticação",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(this, "logou", Toast.LENGTH_LONG).show()
                    val i = Intent(this, PokemonListActivity::class.java)
                    startActivity(i)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("nfoi", "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "nao logou", Toast.LENGTH_LONG).show()
                }
            }
    }

}