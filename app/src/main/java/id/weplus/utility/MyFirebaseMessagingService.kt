package id.weplus.utility

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import id.weplus.MainActivity
import id.weplus.R
import org.json.JSONObject

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("test","got remote message ${remoteMessage.toString()}")
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            var message = ""
            it.body?.let { b ->
                message = b
                val data = remoteMessage.data
                val type = data["type"] ?: "chat"
                val pendingIntent = generatePendingIntent(data)
                sendNotification(pendingIntent, message, it.title)
            }
        }
    }

    override fun onNewToken(token: String) {
        Log.d("fcm", "token : $token")
    }

    private fun generatePendingIntent(data: Map<String, String>): PendingIntent {
        val intent: Intent?

        Log.d("notif", "open home activity")
        intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        return PendingIntent.getActivity(
                this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT
        )
    }

    private fun sendNotification(pendingIntent: PendingIntent, message: String, title: String?) {
        val channelId = "1"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_logo_we)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    channelId,
                    message,
                    NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())

    }


    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}