package dev.runo.harmony.data.map

import dev.runo.harmony.data.source.MusicApiModel
import dev.runo.harmony.domain.module.Music

object ConvertMusicModel {
    fun toDomainModel(musicApiModel: MusicApiModel): Music {
        return Music(
            id = musicApiModel.id,
            path = musicApiModel.path,
            author = musicApiModel.author,
            title = musicApiModel.title,
            length = musicApiModel.length
        )
    }

    fun toApiModel(musicDomainModel: Music): MusicApiModel {
        return MusicApiModel(
            id = musicDomainModel.id,
            path = musicDomainModel.path,
            author = musicDomainModel.author,
            title = musicDomainModel.title,
            length = musicDomainModel.length
        )
    }
}