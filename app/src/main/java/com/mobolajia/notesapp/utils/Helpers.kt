package com.mobolajia.notesapp.utils

import android.util.Log
import android.util.Patterns

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