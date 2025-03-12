package dev.runo.harmony.presentation.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.getSystemService
import dagger.hilt.android.AndroidEntryPoint
import dev.runo.harmony.R
import dev.runo.harmony.domain.repository.MusicRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MusicForegroundService : Service() {

    private val binder = MusicBinder()
    private val serviceScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    @Inject
    lateinit var musicRepository: MusicRepository

    private val notificationChannelId = "MUSIC_PLAYER_NOTIFICATION"
    private val notificationChannelName = "Music service"
    private val notificationId = 1024
    private val currentMusicName = musicRepository.getCurrentMusicAsFlow()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        serviceScope.launch {
            currentMusicName.collectLatest {
                updateNotification("${it.author} - ${it.title}")
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val music = currentMusicName.replayCache.last()

        ServiceCompat.startForeground(
            this,
            notificationId,
            createNotification("${music.author} - ${music.title}"),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            }
            else {
                0
            }
        )
        return START_REDELIVER_INTENT
    }

    override fun onBind(p0: Intent?): IBinder {
        return binder
    }

    override fun onDestroy() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
        super.onDestroy()
    }

    inner class MusicBinder : Binder() {
        fun getService(): MusicForegroundService = this@MusicForegroundService
    }

    /*
    TODO:
    1. use setOngoing to make non-closable player when track playing
    2. change icon
     */
    private fun createNotification(musicName: String): Notification {
        return NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle(musicName)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.app_name))
            .build()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(notificationChannelId, notificationChannelName, NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService<NotificationManager>()!!
        notificationManager.createNotificationChannel(channel)
    }

    private fun updateNotification(name: String) {
        getSystemService<NotificationManager>()!!.notify(notificationId, createNotification(name))
    }
}