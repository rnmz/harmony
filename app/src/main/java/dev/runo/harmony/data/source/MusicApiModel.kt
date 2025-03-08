package dev.runo.harmony.data.source

import android.net.Uri

data class MusicApiModel(
    val id: Long,
    val path: Uri,
    val author: String,
    val title: String,
    val length: Int // in seconds
)
