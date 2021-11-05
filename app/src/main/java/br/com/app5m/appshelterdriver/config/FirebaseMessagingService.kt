package br.com.app5m.appshelterdriver.config

import android.annotation.SuppressLint
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
import br.com.app5m.appshelterdriver.ui.activity.HomeAct
import br.com.app5m.appshelterdriver.util.Preferences
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService: FirebaseMessagingService() {

    private val TAG = "notifica"
    private var intent: Intent? = null

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        //log para ver os dados que estao vindo
        Log.i(TAG, "onMessageReceived: " + remoteMessage.data)

        if (remoteMessage.data.isNotEmpty()) {
            val title = remoteMessage.data["titulo"]
            val body = remoteMessage.data["descricao"]
            val type = remoteMessage.data["type"]

            setMessage(title, body, type)
        }
    }

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.i(TAG, "onNewToken: $s")
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setMessage(title: String?, body: String?, type: String?) {

        val channel = getString(R.string.default_notification_channel_id)
        val uriSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var pendingIntent: PendingIntent? = null
        var defaultNotification: String? = null

        when (type) {
            "SOLICITADA" -> {
                //1
                //id_corrida para o motorista aceitar
                intent = Intent(this, HomeAct::class.java)
                intent!!.putExtra("", "")
                pendingIntent = PendingIntent.getActivity(
                    this,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
            "ACEITA" -> {
                //2
                //id_corrida para o motorista iniciar
                intent = Intent(this, HomeAct::class.java)
                intent!!.putExtra("", "")
                intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            "EM_ANDAMENTO" -> {
                //3
                // envia algum dado chave para colocar a tela em ongoing
                intent = Intent(this, HomeAct::class.java)
                intent!!.putExtra("", "")
                intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            "FINALIZADA" -> {
                //4
                // envia algum dado chave para colocar a tela em finish
                intent = Intent(this, HomeAct::class.java)
                intent!!.putExtra("", "")
                intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            "CANCELADA_MOTORISTA" -> {
                //5
                // envia algum dado chave para colocar a tela em overview e mostrar mensagem q cancelou
                intent = Intent(this, HomeAct::class.java)
                intent!!.putExtra("", "")
                intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            "CANCELADA_PASSAGEIRO" -> {
                //6
                // envia algum dado chave para colocar a tela em overview e mostrar mensagem q cancelou
                intent = Intent(this, HomeAct::class.java)
                intent!!.putExtra("", "")
                intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            //DEFAULT NOTIFICATIONS
            else -> {
//                intent = Intent(this, NotificationAct::class.java)
//                pendingIntent = PendingIntent.getActivity(
//                    this,
//                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT
//                )
            }
        }
        val notification = NotificationCompat.Builder(this, channel)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setSound(uriSound)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nChannel =
                NotificationChannel(channel, "channel", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(nChannel)
        }
        notificationManager.notify(0, notification.build())

    }

}