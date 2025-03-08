package dev.runo.harmony.domain.module

import android.net.Uri

data class Music(
    val id: Long,
    val path: Uri,
    val author: String,
    val title: String,
    val length: Int // seconds
)