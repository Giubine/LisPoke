package com.example.listpoke.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listpoke.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CadastroActivitiy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        var email1 = findViewById<EditText>(R.id.edt_email)
        var senha1 = findViewById<EditText>(R.id.edt_senha)
        var btn_cadastro = findViewById<Button>(R.id.btn_cadastrar)



        var auth: FirebaseAuth = Firebase.auth

        btn_cadastro.setOnClickListener {

            var email = email1.text.toString().trim()
            var senha = senha1.text.toString().trim()

            auth.createUserWithEmailAndPassword(email , senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(baseContext, "Usuário criado", Toast.LENGTH_LONG).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("não sucesso", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Erro ao criar usuário",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }


}