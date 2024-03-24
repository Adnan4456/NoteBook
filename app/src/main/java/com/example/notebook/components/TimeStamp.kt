package com.example.notebook.components

import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun formatTimestamp(timestamp: Long): String {

    val instant = Instant.ofEpochMilli(timestamp)

    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy\nHH:mm")

    // Convert Instant to formatted String
    return formatter.format(instant.atZone(ZoneId.systemDefault()))
}