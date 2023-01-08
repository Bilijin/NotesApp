package com.mobolajia.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val _registrationStatus = MutableStateFlow("n/a")
    val registrationStatus = _registrationStatus.asStateFlow()
    private val _addUserDbStatus = MutableStateFlow("n/a")
    val addUserDbStatus = _addUserDbStatus.asStateFlow()

    init {
        if (auth.currentUser != null) {
            auth.signOut()
        }
    }

    fun createUserAccount(email :String, password : String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            when {
                task.isSuccessful -> _registrationStatus.update { "success" }
                task.isCanceled -> _registrationStatus.update { "failed" }
                else -> _registrationStatus.update { task.exception?.message.orEmpty() }
            }

            _registrationStatus.update { "n/a" }
        }
    }

    fun updateUserDetails(firstName : String, lastName: String) {

        val profileInfo = userProfileChangeRequest {
            displayName = "$firstName $lastName"
        }

        auth.currentUser?.updateProfile(profileInfo)?.addOnSuccessListener {
            _addUserDbStatus.update { "success" }
            _addUserDbStatus.update { "n/a" }
        }?.addOnFailureListener { exception ->
            _addUserDbStatus.update { exception.message.orEmpty() }
            _addUserDbStatus.update { "n/a" }
        }
    }
}