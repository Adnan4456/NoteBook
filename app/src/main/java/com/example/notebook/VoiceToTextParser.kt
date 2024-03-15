package com.example.notebook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class VoiceToTextParser
    @Inject
    constructor(
   @ApplicationContext private val app: Context
): RecognitionListener {


    private val _state = MutableStateFlow(VoiceToTextParserState())

    val state = _state.asStateFlow()

    val recognizer = SpeechRecognizer.createSpeechRecognizer(app)

    fun startListensing(languageCode:String = "en"){
        _state.update {
            VoiceToTextParserState()
        }

        if (!SpeechRecognizer.isRecognitionAvailable(app)){
            _state.update {
                it.copy(
                    error = "Recognizer is not avaliable"
                )
            }
        }

        val intent  =  Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
          //  putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
            //it will increase time of slience of user
            //putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 5000)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
        }

        recognizer.setRecognitionListener(this)
        recognizer.startListening(intent)

        _state.update {
            it.copy(
                isSpeaking = true
            )
        }
    }

    fun stopListensing(){

        _state.update {
            it.copy(
                isSpeaking = false
            )
        }
        recognizer.stopListening()
    }
    override fun onReadyForSpeech(p0: Bundle?) {
        _state.update {
            it.copy(
                error = null
            )
        }
    }

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(p0: Float)  = Unit

    override fun onBufferReceived(p0: ByteArray?) = Unit

    override fun onEndOfSpeech() {
        _state.update {
            it.copy(
                isSpeaking = false
            )
        }
    }

    override fun onError(error: Int) {
         if (error == SpeechRecognizer.ERROR_CLIENT){
             return
         }
        _state.update {
            it.copy(
                error = "Error: $error"
            )
        }
    }

    override fun onResults(results: Bundle?) {
       results
           ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
           ?.getOrNull(0)
           ?.let {result ->
               _state.update {
                   it.copy(
                       spokenText = result
                   )
               }
           }
    }

    override fun onPartialResults(p0: Bundle?) = Unit

    override fun onEvent(p0: Int, p1: Bundle?)= Unit
}

data class VoiceToTextParserState(
    val spokenText: String = "",
    val isSpeaking: Boolean = false,
    val error:String? = null,

)