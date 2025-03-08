package dev.runo.harmony.data.repository

import dev.runo.harmony.common.ErrorType
import dev.runo.harmony.common.WorkStatus
import dev.runo.harmony.data.convertToSortType
import dev.runo.harmony.data.map.ConvertMusicModel
import dev.runo.harmony.data.source.MusicFileApi
import dev.runo.harmony.domain.module.Music
import dev.runo.harmony.domain.repository.MusicRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class LocalMusicRepository @Inject constructor(
    private val musicFileApi: MusicFileApi
) : MusicRepository {

    private val _currentMusic = MutableSharedFlow<Music>(replay = 1)
    val currentMusic: SharedFlow<Music> = _currentMusic.asSharedFlow()

    override suspend fun getAllMusic(sortType: Boolean): Flow<WorkStatus<Music>> = musicFileApi
        .getAllMusic(sortType.convertToSortType())
        .onStart {
            delay(1000L)
            WorkStatus.Loading
        }
        .onEmpty {
            WorkStatus.Error(ErrorType.Local.NOT_FOUND)
        }
        .map {
            WorkStatus.Success(ConvertMusicModel.toDomainModel(it))
        }
}