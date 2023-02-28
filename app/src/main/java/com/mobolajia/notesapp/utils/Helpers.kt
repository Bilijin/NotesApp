package com.mobolajia.notesapp.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment

fun String.isValidEmail(): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun isInternetConnected(): Boolean =
    try {
        val command = "ping -c 1 google.com"
        Runtime.getRuntime().exec(command).waitFor() == 0
    } catch (e: Exception) {
        Log.d("internet", "internet error")
        false
    }

fun String.validatePassword() : String {
    return when {
        length < 6 -> "Password too short. Minimum 6 characters"
        !contains(Regex("\\d")) -> "Password must contain at least one number"
        else -> "true"
    }
}

fun Fragment.showMessageDialog(text : String) {
    val dialog = AlertDialog.Builder(this.requireContext())
    dialog.setCancelable(true)
    dialog.setMessage(text)
    dialog.create().show()
}