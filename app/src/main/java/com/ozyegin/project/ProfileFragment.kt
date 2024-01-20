package com.ozyegin.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ozyegin.project.adapters.ProfileAdapter
import com.ozyegin.project.data.Profile
import java.security.MessageDigest
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var recyclerView: RecyclerView
    private lateinit var profileAdapter: ProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!

        recyclerView = view.findViewById(R.id.profileRecyclerView)
        profileAdapter = ProfileAdapter(getProfileData())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = profileAdapter

        return view
    }

    private fun getProfileData(): Profile {
        val randomPassword = generateRandomPassword()

        val signUpDate = user.metadata?.creationTimestamp?.let { timestamp ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timestamp
            calendar.time
        } ?: Date()
        println(user.email)
        return Profile(
            user.email ?: "",
            randomPassword,
            user.displayName ?: "",
            signUpDate.toString(),
        )
    }

    private fun generateRandomPassword(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val password = (1..8).map { chars.random() }.joinToString("")
        return password
    }
}
