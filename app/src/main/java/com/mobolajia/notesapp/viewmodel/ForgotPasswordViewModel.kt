package com.mobolajia.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {
    private val auth = Firebase.auth
    private val _resendEmailStatus = MutableStateFlow(String())
    val resendEmailStatus = _resendEmailStatus.asStateFlow()

    fun resetPassword(email : String) {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                _resendEmailStatus.update {
                    "success"
                }
            }
            .addOnFailureListener { exception ->
                _resendEmailStatus.update {
                    exception.localizedMessage.orEmpty()
                }
            }
    }
}