package com.example.hw7.service

import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.hw7.R
import com.example.hw7.domain.usecase.AddPushTokenUseCase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseNotificationService : FirebaseMessagingService() {

    companion object {
        private const val POST_CHANNEL_ID = "posts"
        private var postNotificationId = 1
    }

    @Inject
    lateinit var addPushTokenUseCase: AddPushTokenUseCase

    override fun onCreate() {
        super.onCreate()
        createChannel()
    }

    override fun onNewToken(token: String) {
        Log.d("token", token)
        addPushTokenUseCase(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let {
            val notification = NotificationCompat.Builder(this, POST_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(it.title)
                .setContentText(it.body)
                .build()
            NotificationManagerCompat.from(this)
                .notify(postNotificationId++, notification)

        }
    }

    private fun createChannel() {
        val channel = NotificationChannelCompat.Builder(
            POST_CHANNEL_ID,
            NotificationManagerCompat.IMPORTANCE_HIGH
        )
            .setName("New posts")
            .build()
        NotificationManagerCompat.from(applicationContext)
            .createNotificationChannel(channel)
    }
}