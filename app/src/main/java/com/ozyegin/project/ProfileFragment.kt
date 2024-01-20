package com.ozyegin.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!

        // Inflate the profile content layout
        val profileContent = inflater.inflate(R.layout.profile_fragment, container, false)

        // Populate the views in the profile content layout
        val profileName = profileContent.findViewById<TextView>(R.id.profileName)
        val profileEmail = profileContent.findViewById<TextView>(R.id.profileEmail)
        val profilePass = profileContent.findViewById<TextView>(R.id.profilePass)
        val profileDate = profileContent.findViewById<TextView>(R.id.profileDate)

        profileName.text = user.displayName ?: ""
        profileEmail.text = user.email ?: ""
        profilePass.text = generateRandomPassword()
        profileDate.text = user.metadata?.creationTimestamp?.let { timestamp ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timestamp
            calendar.time.toString()
        } ?: ""

        return view
    }

    private fun generateRandomPassword(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..8).map { chars.random() }.joinToString("")
    }
}

