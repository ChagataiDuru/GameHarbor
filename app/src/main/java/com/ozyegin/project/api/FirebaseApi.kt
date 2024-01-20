package com.ozyegin.project.api

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import com.ozyegin.project.data.UserEntity
import java.util.*

class FirebaseApi {

    private val database = FirebaseDatabase.getInstance().getReference("")
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    val createUserSuccessful: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val createUserError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }
    val loginSuccessful: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val loginError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }
    val loginFirebaseError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }
    val restorePasswordSuccessful: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val restorePasswordError: MutableLiveData<Exception> by lazy { MutableLiveData<Exception>() }

    /**
     * Gets the current user email
     */
    fun getEmail(): String? {
        return auth.currentUser!!.email
    }

    /**
     * Creates an user
     */
    fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = UserEntity()
                    user.email = email

                    val userRef = database.child("user")
                    userRef.child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .setValue(user)
                        .addOnSuccessListener {
                            createUserSuccessful.value = true
                        }.addOnFailureListener {
                            createUserError.value = it
                        }
                } else {
                    createUserError.value = task.exception
                }
            }
    }

    /**
     * Login using email and password
     */
    fun loginWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                loginSuccessful.value = true
            }.addOnFailureListener {
                loginError.value = it
            }
    }

    /**
     * Login using a Google account
     */
    fun loginWithGoogle(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val idToken: String

        try {
            idToken = task.getResult(ApiException::class.java)!!.idToken!!
        } catch (e: ApiException) {
            loginFirebaseError.value = e
            return
        }

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { taskSing ->
            if (taskSing.isSuccessful) {
                // Check if the user is registered in Firebase
                database.child("user").child(taskSing.result!!.user!!.uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            createUserError.value = error.toException()
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.value == null) {
                                val user = UserEntity()
                                user.email = FirebaseAuth.getInstance().currentUser!!.email!!

                                val userRefToAdd = database.child("user")
                                userRefToAdd.child(FirebaseAuth.getInstance().currentUser!!.uid)
                                    .setValue(user)
                                    .addOnSuccessListener {
                                        createUserSuccessful.value = true
                                        loginSuccessful.value = true
                                    }.addOnFailureListener {
                                        createUserError.value = it
                                    }
                            } else {
                                createUserSuccessful.value = true
                                loginSuccessful.value = true
                            }
                        }
                    })
            } else {
                loginFirebaseError.value = taskSing.exception
            }
        }
    }

    /**
     * Restores the password of the user
     */
    fun restorePassword(email: String) {
        val language: String =
            if (Locale.getDefault().language.uppercase(Locale.ROOT) == "ES") {
                Locale.getDefault().toLanguageTag()
            } else {
                "en-US"
            }

        auth.setLanguageCode(language)
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    restorePasswordSuccessful.value = true
                } else {
                    restorePasswordError.value = task.exception
                }
            }
    }
}