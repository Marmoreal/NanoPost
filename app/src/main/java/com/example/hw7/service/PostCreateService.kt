package com.example.hw7.service

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.hw7.R
import com.example.hw7.domain.usecase.CreatePostUseCase
import com.example.hw7.domain.usecase.GetContentUriUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PostCreateService : Service(), CoroutineScope by MainScope() {

    companion object {
        private const val ACTION_EXECUTE = "com.example.hw7.action.EXECUTE"
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_PROGRESS = "progress"
        private const val EXTRA_TEXT = "com.example.hw7.extras.TEXT"
        private const val EXTRA_IMAGE = "com.example.hw7.extras.IMAGE"

        fun newIntent(context: Context, text: String?, images: ArrayList<Uri>?) = Intent(
            context, PostCreateService::class.java
        ).apply {
            action = ACTION_EXECUTE
            putExtra(EXTRA_TEXT, text)
            putExtra(EXTRA_IMAGE, images)
        }
    }

    @Inject
    lateinit var createPostUseCase: CreatePostUseCase

    @Inject
    lateinit var getContentUriUseCase: GetContentUriUseCase

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_EXECUTE) {
            startForeground(NOTIFICATION_ID, createNotification())
            val bundle = intent.extras
            launch {
                val arrayUri = bundle?.getParcelableArrayList<Uri>(EXTRA_IMAGE)
                val listByteArray = arrayUri?.map {
                    getContentUriUseCase(it)
                }
                createPostUseCase(bundle?.getString(EXTRA_TEXT), listByteArray)
                stopSelf()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotification(): Notification {
        createChannel()
        return NotificationCompat.Builder(this, CHANNEL_PROGRESS)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Creating post...")
            .setProgress(0, 0, true)
            .build()
    }

    private fun createChannel() {
        val channel = NotificationChannelCompat.Builder(
            CHANNEL_PROGRESS, NotificationManagerCompat.IMPORTANCE_LOW
        )
            .setName("Data upload")
            .build()
        NotificationManagerCompat.from(this)
            .createNotificationChannel(channel)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}