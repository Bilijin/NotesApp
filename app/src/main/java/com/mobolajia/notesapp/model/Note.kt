package com.mobolajia.notesapp.model

import java.io.Serializable


data class Note(
    var title : String = "",
    var time_created : String ="",
    var text : String = "",
    var id : Int = 0
) : Serializable
