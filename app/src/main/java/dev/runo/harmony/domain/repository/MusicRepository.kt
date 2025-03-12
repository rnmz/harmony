package dev.runo.harmony.domain.repository

import dev.runo.harmony.common.WorkStatus
import dev.runo.harmony.domain.module.Music
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface MusicRepository {
    suspend fun getAllMusic(sortType: Boolean): Flow<WorkStatus<Music>>
    suspend fun searchByFilters(author: String?, title: String?, genres: List<String>?): Flow<WorkStatus<Music>>
    fun getCurrentMusicAsFlow(): SharedFlow<Music>
    suspend fun setCurrentMusic(music: Music)
}