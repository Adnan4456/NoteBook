package com.example.notebook.feature_note.presentation.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


enum class ChipType(val title: String) {
    ALL("All"),
    NOTES("Notes"),
    TODO("Todo"),
}

@Composable
fun Chip(
    chipType: ChipType,
    selectedChip: ChipType,
    onChipClicked: (ChipType) -> Unit
){

    val isSelected = chipType == selectedChip
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else Color.White

    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .background(backgroundColor)
            .clickable { onChipClicked(chipType) }
            .padding(start = 16.dp , end = 16.dp , top = 8.dp, bottom = 8.dp)
    ) {
        Text(
            text = chipType.title,
            color = contentColor,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ChipRow(
    selectedChip: ChipType,
    onChipClicked: (ChipType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Chip(ChipType.ALL, selectedChip, onChipClicked)
        Chip(ChipType.NOTES, selectedChip, onChipClicked)
        Chip(ChipType.TODO, selectedChip, onChipClicked)
    }
}