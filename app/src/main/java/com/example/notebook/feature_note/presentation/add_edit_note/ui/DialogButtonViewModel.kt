package com.example.notebook.feature_note.presentation.add_edit_note.ui

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class DialogButtonViewModel
    @Inject constructor()
    :ViewModel() {

    var isBold = mutableStateOf(false)
    var italicSelected = mutableStateOf(false)
    var underlineSelected = mutableStateOf(false)
    var titleSelected = mutableStateOf(false)
    var subtitleSelected = mutableStateOf(false)
    var textColorSelected = mutableStateOf(false)
    var linkSelected = mutableStateOf(false)
    var alignmentSelected = mutableStateOf(1)


}