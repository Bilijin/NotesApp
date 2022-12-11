package com.mobolajia.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {
    private val auth : FirebaseAuth = Firebase.auth
    private val _registrationStatus = MutableStateFlow("n/a")
    val registrationStatus = _registrationStatus.asStateFlow()

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
}