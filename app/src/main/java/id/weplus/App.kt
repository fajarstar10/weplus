package id.weplus

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.google.firebase.analytics.FirebaseAnalytics

class App : Application() {

    companion object {
        const val notificationChannelID = "TestChannel"
        lateinit var mFirebaseAnalytics: FirebaseAnalytics
    }
    // Customize the notification channel as you wish. This is only for a bare minimum example
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                    notificationChannelID,
                    "TestApp Channel",
                    NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun onCreate() {
        super.onCreate()
        mFirebaseAnalytics  = FirebaseAnalytics.getInstance(this)
        createNotificationChannel()
    }
}