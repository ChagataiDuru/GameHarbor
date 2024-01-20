package com.ozyegin.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.ozyegin.project.databinding.LoginFragmentBinding
import android.util.Log

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: TextInputLayout
    private lateinit var passwordEditText: TextInputLayout
    private lateinit var loginButton: Button
    private lateinit var signInButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)
        val binding = LoginFragmentBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        emailEditText = binding.editTextEmail
        passwordEditText = binding.editTextPassword
        loginButton = view.findViewById(R.id.btnLogin)
        signInButton = view.findViewById(R.id.btnRegister)

        loginButton.setOnClickListener {
            val email = emailEditText.get(0).toString()
            val password = passwordEditText.get(0).toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Login successful, you can navigate to the next screen
                        // or perform other actions as needed
                    } else {
                        // Login failed, handle the error
                    }
                }

        }
        signInButton.setOnClickListener {
            val email = emailEditText.get(0).toString()
            Log.d("email", email)
            val password = passwordEditText.get(0).toString()
            Log.d("password", password)

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign up successful, you can navigate to the next screen
                        // or perform other actions as needed
                    } else {
                        // Sign up failed, handle the error
                    }
                }
        }

        return view


    }
}
