package dev.runo.harmony.data.source

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import dev.runo.harmony.data.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

// WARNING: DO NOT TOUCH
private typealias AudioStore = MediaStore.Audio.Media

@Singleton
class MusicFileApi @Inject constructor(context: Context) {

    private val resolver = context.contentResolver
    private val projection = arrayOf(
        AudioStore._ID,
        AudioStore.TITLE,
        AudioStore.DURATION,
        AudioStore.ARTIST
    )

    fun getAllMusic(sortType: SortType): Flow<MusicApiModel> = flow {
        val sort = if (sortType == SortType.ASCENDING) "ASC" else "DESC"

        val cursor = resolver.query(
            AudioStore.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            "${AudioStore._ID} $sort"
        )

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    emit(
                        rawToMusicModel(it)
                    )
                } while (it.moveToNext())
            }
        }
    }.flowOn(Dispatchers.IO)

    fun searchMusicByFilters(
        author: String? = null,
        title: String? = null
    ): Flow<MusicApiModel> = flow {
        val selectionParts = mutableListOf<String>()
        val selectionArgs = mutableListOf<String>()

        if (!author.isNullOrBlank()) {
            selectionParts.add("${AudioStore.ARTIST} = ?")
            selectionArgs.add(author)
        }
        if (!title.isNullOrBlank()) {
            selectionParts.add("${AudioStore.TITLE} = ?")
            selectionArgs.add(title)
        }

        val selectionString = selectionParts.takeIf { it.isNotEmpty() }?.joinToString (" AND ")

        val cursor = resolver.query(
            AudioStore.EXTERNAL_CONTENT_URI,
            projection,
            selectionString,
            selectionArgs.toTypedArray(),
             "${AudioStore._ID} ASC"
        )

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    emit(
                        rawToMusicModel(it)
                    )
                } while (it.moveToNext())
            }
        }
    }.flowOn(Dispatchers.IO)

    @SuppressLint("Range")
    private fun rawToMusicModel(cursor: Cursor): MusicApiModel {
        return MusicApiModel(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(AudioStore._ID)),
            path = ContentUris.withAppendedId(
                AudioStore.EXTERNAL_CONTENT_URI,
                cursor.getLong(cursor.getColumnIndexOrThrow(AudioStore._ID))
            ),
            author = cursor.getString(cursor.getColumnIndex(AudioStore.ARTIST))
                ?: "Unknown",
            length = cursor.getInt(cursor.getColumnIndex(AudioStore.DURATION)),
            title = cursor.getString(cursor.getColumnIndex(AudioStore.TITLE))
                ?: "Unknown"
        )
    }
}