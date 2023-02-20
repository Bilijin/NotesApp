package com.mobolajia.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {
    private lateinit var user: FirebaseUser
    private val _name = MutableStateFlow(String())
    val name = _name.asStateFlow()
    private val _email = MutableStateFlow(String())
    val email = _email.asStateFlow()
    private val _updatePassword = MutableStateFlow(String())
    val updatePassword = _updatePassword.asStateFlow()

    init {
        if (Firebase.auth.currentUser != null) user = Firebase.auth.currentUser!!
        getUserDetails()
    }

    private fun getUserDetails() {
        user.let { firebaseUser ->
            firebaseUser.displayName?.let { n -> _name.update { n } }
            firebaseUser.email?.let { e -> _email.update { e } }
        }
    }

    fun checkPasswordAndChange(password: String, newPassword : String) {
        val credential = EmailAuthProvider.getCredential(user.email!!, password)
        user.reauthenticate(credential).addOnSuccessListener { updatePassword(newPassword) }
            .addOnFailureListener { exception ->
                _updatePassword.update { "failed ${exception.message}" }
                _updatePassword.update { String() }
            }
    }

    private fun updatePassword(password: String) {
        user.updatePassword(password).addOnSuccessListener {
            _updatePassword.update { "success" }
            _updatePassword.update { String() }
        }.addOnFailureListener { exception ->
            _updatePassword.update { "failed ${exception.message}" }
            _updatePassword.update { String() }
        }
    }
}