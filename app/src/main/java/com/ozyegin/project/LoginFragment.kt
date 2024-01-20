package com.ozyegin.project

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ozyegin.project.activity.MainActivity
import com.ozyegin.project.databinding.LoginFragmentBinding
import androidx.lifecycle.ViewModelProvider
import com.ozyegin.project.viewmodels.LoginViewModel



class LoginFragment : Fragment() {

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("239294129763-j0v1enbvu94da0bv7pbimqppk6tfm1ej.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Success in the login
        val loginSuccessful = Observer<Boolean> { loginSuccessful ->
            if (loginSuccessful) {
                val intent = Intent(activity, MainActivity::class.java)
                (childFragmentManager.findFragmentByTag("progress") as? DialogFragment)?.dismiss()
                startActivity(intent)
                requireActivity().finish()
            }
        }

        // Error in the login
        val loginException = Observer<Exception> {
            (childFragmentManager.findFragmentByTag("progress") as? DialogFragment)?.dismiss()

            Toast.makeText(
                requireContext(),
                requireActivity().getString(R.string.error_incorrect_user_or_password),
                Toast.LENGTH_SHORT
            ).show()
        }

        viewModel.loginSuccessful.observe(this, loginSuccessful)
        viewModel.loginException.observe(this, loginException)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Press the login button
        binding.btnLogin.setOnClickListener {
            if (validateLogin()) {
                viewModel.login(
                    binding.editTextEmail.editText?.text.toString(),
                    binding.editTextPassword.editText?.text.toString()
                )
            }
        }

        binding.btnLoginGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, 1007)
        }

        binding.btnRegister.setOnClickListener {
            auth
        }

        binding.btnRestorePassword.setOnClickListener {
            // Pass the email to the restore password fragment
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            viewModel.loginWithGoogle(it.data)
        }
    }

    /**
     * Function called with the result of the login with Google
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1007) {
            viewModel.loginWithGoogle(data)
        }
    }

    /**
     * Validates the login fields
     */
    private fun validateLogin(): Boolean {
        val email = binding.editTextEmail.editText?.text.toString()
        val passwd = binding.editTextPassword.editText?.text.toString()

        if (email.isBlank()) {
            binding.editTextEmail.error = requireActivity().getString(R.string.error_required)
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.error = requireActivity().getString(R.string.error_mail_not_valid)
            return false
        }

        if (passwd.isBlank()) {
            binding.editTextPassword.error = requireActivity().getString(R.string.error_required)
            return false
        } else if (passwd.length < 4) {
            binding.editTextPassword.error = requireActivity().getString(R.string.error_password_length)
            return false
        }

        return true
    }

}
