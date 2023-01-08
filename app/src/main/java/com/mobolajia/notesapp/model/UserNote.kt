package com.mobolajia.notesapp.model

data class UserNote(
    var user_notes : List<Note>,
    var note_count : Int = 0
)
