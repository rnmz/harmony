package dev.runo.harmony.presentation.intent

sealed interface MusicIntent {
    data object Play : MusicIntent
    data object Pause : MusicIntent
    data class SeekTo(val position: Long) : MusicIntent
}