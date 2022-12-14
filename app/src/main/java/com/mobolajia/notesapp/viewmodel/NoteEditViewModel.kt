package com.mobolajia.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.mobolajia.notesapp.model.Note
import com.mobolajia.notesapp.model.User
import kotlinx.coroutines.flow.update

class NoteEditViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private val notesRef = db.collection("notes/${auth.currentUser?.uid.toString()}/userNotes")
    private var user : User? = User()

    init {
        getUser()
    }

    fun saveNote(note : Note) {
        note.id = user?.note_count?.plus(1)!!

        notesRef.document(note.id.toString()).set(note)
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }

    fun updateNote(note : Note) {
//        notesRef.document().set()
    }

    fun updateNoteCount() {
        db.collection("users").document(auth.currentUser?.uid.toString())
            .update("note_count", user?.note_count?.plus(1))
    }

    private fun getUser() {
        val userRef = db.collection("users")
            .document(auth.currentUser?.uid.toString())
        userRef.get().addOnSuccessListener { docSnap ->
//            _name.update {
                user = docSnap.toObject<User>()
//            }
        }

    }
}