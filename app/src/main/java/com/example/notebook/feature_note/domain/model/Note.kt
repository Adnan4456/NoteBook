package com.example.notebook.feature_note.domain.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notebook.ui.theme.*

@Entity
data  class Note (

    @PrimaryKey
    val  id: Int? = null,

    val title: String,
    val content : String,
    val timestamp: Long,
    val color: Int,
    val isBookMarked : Boolean = false,
    val isSecrete: Boolean = false,
    val imageBitmap: Bitmap? = null
){

    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}


//class InvalidNoteException(message: String)
//    :Exception(message)