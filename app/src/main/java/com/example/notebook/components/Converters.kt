package com.example.notebook.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.example.notebook.feature_todo.domain.model.ChecklistItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream

class Converters {

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray?{
        if (bitmap == null) {
            return null
        }
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100 , outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray?): Bitmap?{
        if (byteArray == null) {
            return null
        }
        return  BitmapFactory.decodeByteArray(byteArray , 0 , byteArray.size)
    }

    @TypeConverter
    fun fromJson(json: String): List<ChecklistItem> {
        val type = object : TypeToken<List<ChecklistItem>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(checklist: List<ChecklistItem>): String {
        return Gson().toJson(checklist)
    }
}