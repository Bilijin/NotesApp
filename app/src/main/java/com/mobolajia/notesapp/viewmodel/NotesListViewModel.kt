package com.mobolajia.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.mobolajia.notesapp.model.Note
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
    private var _noteCount = MutableStateFlow(0)
    val noteCount = _noteCount.asStateFlow()
    private val _delNote = MutableStateFlow(String())
    val delNote = _delNote.asStateFlow()

    init {
        getUserName()
    }

    fun getNotes() {
        val notesRef = db.collection("notes/${auth.currentUser?.uid.toString()}/userNotes")
        notesRef.get().addOnSuccessListener { querySnap ->
            val notes = ArrayList<Note>()
            querySnap.documents.forEach { docSnap ->
                docSnap.toObject<Note>()?.let { notes.add(it) }
            }
            _noteCount.update { notes.size }
            _listOfNotes.update { notes }
        }
    }

    private fun getUserName() {
        _name.update {
            auth.currentUser?.displayName.toString()
        }
    }

    fun deleteNote(noteId: Int) {
        val noteRef = db.document("notes/${auth.currentUser?.uid.toString()}/userNotes/$noteId")
        noteRef.delete().addOnSuccessListener {
            _delNote.update { "true" }
            getNotes()
        }.addOnFailureListener {
            _delNote.update { "false" }
        }
    }

}