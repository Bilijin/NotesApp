package com.mobolajia.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileViewModel : ViewModel() {
    val auth = Firebase.auth

    init {

    }

    private fun getUserDetails() {
        val user = auth.currentUser

        user?.let {
            it.
        }
    }
}