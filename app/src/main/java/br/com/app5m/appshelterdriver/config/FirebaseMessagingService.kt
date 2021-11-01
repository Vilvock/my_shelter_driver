package br.com.app5m.appshelterdriver.config

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import br.com.app5m.appshelterdriver.MainAct
import br.com.app5m.appshelterdriver.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.i(TAG, "onMessageReceived: " + remoteMessage.data)

        if (remoteMessage.data.isNotEmpty()) {

            val title = remoteMessage.data["titulo"]
            val body = remoteMessage.data["descricao"]

            setMessage("body", "title")
        }
    }

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.i(TAG, "onNewToken: ")
    }

    private fun setMessage(body: String?, title: String?) {

        val channel = getString(R.string.default_notification_channel_id)
        val uriSom = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val intent = Intent(this, MainAct::class.java)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val notification = NotificationCompat.Builder(this, channel)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setSound(uriSom)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nChannel = NotificationChannel(channel, "channel", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(nChannel)
        }

        notificationManager.notify(0, notification.build())

    }

    companion object {
        private const val TAG = "notifica"
    }

}