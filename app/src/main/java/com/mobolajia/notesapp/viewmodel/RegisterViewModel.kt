package com.mobolajia.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {
    private var auth : FirebaseAuth = Firebase.auth
    private val _registrationStatus = MutableStateFlow("n/a")
    val registrationStatus = _registrationStatus.asStateFlow()

    fun createUserAccount(email :String, password : String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                //emit successful
                _registrationStatus.update { "success" }
            } else if (it.isCanceled){
                //emit not successful
                _registrationStatus.update { "failed" }
            }
        }
    }
}