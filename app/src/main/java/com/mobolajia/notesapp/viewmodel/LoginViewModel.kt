package com.mobolajia.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val auth = Firebase.auth
    private val _loginStatus = MutableStateFlow("n/a")
    val loginStatus = _loginStatus.asStateFlow()

    init {
        if (auth.currentUser != null) {
            auth.signOut()
        }
    }

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            when {
                task.isSuccessful -> _loginStatus.update { "success" }
                task.isCanceled -> _loginStatus.update { "failed" }
                else -> _loginStatus.update { task.exception?.message.orEmpty() }
            }

            _loginStatus.update { "n/a" }
        }
    }
}