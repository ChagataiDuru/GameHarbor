package com.ozyegin.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
class SignupFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        auth = FirebaseAuth.getInstance()
        emailEditText = view.findViewById(R.id.editTextEmail)
        passwordEditText = view.findViewById(R.id.editTextPassword)
        signUpButton = view.findViewById(R.id.buttonSignUp)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Signup successful, you can navigate to the next screen
                        // or perform other actions as needed
                    } else {
                        // Signup failed, handle the error
                    }
                }
        }

        return view
    }
}
