package com.mobolajia.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobolajia.notesapp.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NoteEditViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private val notesRef = db.collection("notes/${auth.currentUser?.uid.toString()}/userNotes")
    private val _noteSaveStatus = MutableStateFlow(String())
    val noteSaveStatus = _noteSaveStatus.asStateFlow()

    fun saveNote(note: Note) {
        notesRef.document(note.id.toString()).set(note)
            .addOnSuccessListener {
                _noteSaveStatus.update {
                    "success"
                }
            }
            .addOnFailureListener { exception ->
                _noteSaveStatus.update {
                    exception.localizedMessage.orEmpty()
                }
            }
    }

    fun updateNoteCount(count: Int) {
        db.collection("users").document(auth.currentUser?.uid.toString())
            .update("note_count", count)
    }
}