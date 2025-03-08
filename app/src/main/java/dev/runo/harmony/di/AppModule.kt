package dev.runo.harmony.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.runo.harmony.data.repository.LocalMusicRepository
import dev.runo.harmony.data.source.MusicFileApi
import dev.runo.harmony.domain.repository.MusicRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideLocalMusicRepository(musicFileApi: MusicFileApi): MusicRepository {
        return LocalMusicRepository(musicFileApi)
    }

    @Provides
    @Singleton
    fun provideMusicFileApi(@ApplicationContext context: Context): MusicFileApi {
        return MusicFileApi(context)
    }
}