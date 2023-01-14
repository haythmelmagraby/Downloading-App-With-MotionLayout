package com.udacity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

private val NOTIFICATION_ID = 0


fun NotificationManager.sendNotification(status:String,fileName: String,title: String,messageBody: String, applicationContext: Context) {
    val detailIntent = Intent(applicationContext,DetailActivity::class.java)
    detailIntent.putExtra("file_name" , fileName)
    detailIntent.putExtra("status" , status)
    val detailPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        detailIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )


    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.my_notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(title)
        .setContentText(messageBody)
        .addAction(
            R.drawable.ic_launcher_foreground,
            "Check The Status",
            detailPendingIntent
        )
    notify(NOTIFICATION_ID,builder.build())
}
