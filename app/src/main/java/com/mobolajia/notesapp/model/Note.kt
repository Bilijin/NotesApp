package com.mobolajia.notesapp.model

import java.io.Serializable


data class Note(
    var title : String = "",
    var text : String = "",
    var time_created : String ="",
    var id : Int = 0
) : Serializable
