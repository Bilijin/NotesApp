package com.mobolajia.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {
    private val user = Firebase.auth.currentUser
    private val _name = MutableStateFlow(String())
    val name = _name.asStateFlow()
    private val _email = MutableStateFlow(String())
    val email = _email.asStateFlow()

    init {
        getUserDetails()
    }

    private fun getUserDetails() {
        user?.let { firebaseUser ->
            firebaseUser.displayName?.let { n -> _name.update { n } }
            firebaseUser.email?.let { e -> _email.update { e } }
        }
    }
}