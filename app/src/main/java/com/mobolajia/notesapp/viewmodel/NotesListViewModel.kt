package com.mobolajia.notesapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.mobolajia.notesapp.model.Note
import com.mobolajia.notesapp.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NotesListViewModel : ViewModel() {
    private val auth = Firebase.auth
    private val db = Firebase.firestore
    private val _listOfNotes = MutableStateFlow(ArrayList<Note>())
    val listOfNotes = _listOfNotes.asStateFlow()
    private val _name = MutableStateFlow(String())
    val name = _name.asStateFlow()

    init {
        getNotes()
        getUserName()
    }

    private fun getNotes() {
        val notesRef = db.collection("notes/${auth.currentUser?.uid.toString()}/userNotes")
        notesRef.get().addOnSuccessListener { querySnap ->
            val notes = ArrayList<Note>()
            querySnap.documents.forEach { docSnap ->
                docSnap.toObject<Note>()?.let { notes.add(it) }
            }
            _listOfNotes.update { notes }
        }
    }

    private fun getUserName() {
        val userRef = db.collection("users")
            .document(auth.currentUser?.uid.toString())
        userRef.get().addOnSuccessListener { docSnap ->
            _name.update {
                docSnap.toObject<User>()?.first_name.orEmpty()
            }
        }

    }
}