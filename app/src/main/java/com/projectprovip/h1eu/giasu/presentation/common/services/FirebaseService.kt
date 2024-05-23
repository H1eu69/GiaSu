package com.projectprovip.h1eu.giasu.presentation.common.services

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.projectprovip.h1eu.giasu.presentation.MainActivity


class FirebaseService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val context = baseContext

        val channelId = "tutor_request_channel"
        val channelName = "Tutor request Channel"
        val channelDescription = "Channel used to notify when a course is created through learner request tutor"
        val channelImportance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, channelImportance).apply {
            description = channelDescription
        }


        val bundle = Bundle().apply {
            putBoolean("skipSplash", true)
        }

        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            "eds://learning_courses".toUri(),
            context,
            MainActivity::class.java
        )

        deepLinkIntent.putExtras(bundle)

        val deepLinkPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(deepLinkIntent)
            getPendingIntent(0, PendingIntent.FLAG_MUTABLE)
        }


        val notification = NotificationCompat.Builder(context, "tutor_request_channel")
            .setContentTitle(remoteMessage.notification!!.title)
            .setSmallIcon(R.drawable.checkbox_on_background)
            .setContentText(remoteMessage.notification!!.body)
            .setContentIntent(deepLinkPendingIntent)
            .build()


        // Get the NotificationManager and display the notification
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.createNotificationChannel(channel)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(1, notification)
    }
}