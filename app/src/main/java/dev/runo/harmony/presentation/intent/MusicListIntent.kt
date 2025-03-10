package dev.runo.harmony.presentation.intent

import android.net.Uri

sealed interface MusicListIntent {
    data object GetAllInDescending : MusicListIntent
    data object GetAllInAscending : MusicListIntent
    data class PlayMusic(val uri: Uri): MusicListIntent
    data class StopMusic(val uri: Uri): MusicListIntent
}