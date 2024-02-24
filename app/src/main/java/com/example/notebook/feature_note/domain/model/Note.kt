package com.example.notebook.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notebook.ui.theme.*

@Entity
data  class Note (

    @PrimaryKey
    val  id: Int? = null,

    val title: String,
    val content : String,
    val timestamp: String,
    val color: Int,
){

    companion object {

        val noteColors = listOf(RedOrange , LightGreen , Violet , BabyBlue , RedPink)

    }

}