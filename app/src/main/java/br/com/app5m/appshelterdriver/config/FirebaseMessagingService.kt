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
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.activity.HomeAct
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
            val rideId = remoteMessage.data["id_corrida"]

            setMessage(title, body, type, rideId)
        }
    }

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.i(TAG, "onNewToken: $s")
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setMessage(title: String?, body: String?, type: String?, rideId: String?) {

        val channel = getString(R.string.default_notification_channel_id)
        val uriSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var pendingIntent: PendingIntent? = null

        var notifyScreenValue: HomeAct.MainScreenStage? = null
        var rideIdValue: String? = null

        intent = Intent(this, HomeAct::class.java)

        val broadcastManager = LocalBroadcastManager.getInstance(this)
        val intentBroadcast = Intent("Notification")

        when (type) {
            "1" -> {
                //SOLICITADA
                //id_corrida para o motorista aceitar

                intent!!.putExtra("rideId", rideId)
                intentBroadcast.putExtra("rideId", rideId)

            }
            "2" -> {
                //ACEITA
                //envia algum dado chave para colocar a tela em waiting Pickup
                notifyScreenValue = HomeAct.MainScreenStage.WAITING_PICKUP
            }
            "3" -> {
                //EM_ANDAMENTO
                // envia algum dado chave para colocar a tela em ongoing
                notifyScreenValue = HomeAct.MainScreenStage.ONGOING_RIDE
            }
            "4" -> {
                //FINALIZADA
                // envia algum dado chave para colocar a tela em finish
                notifyScreenValue = HomeAct.MainScreenStage.FINISH_RIDE
            }
            "5" -> {
                //CANCELADA_MOTORISTA
                // envia algum dado chave para colocar a tela em overview e mostrar mensagem q cancelou
                notifyScreenValue = HomeAct.MainScreenStage.OVERVIEW
            }
            "6" -> {
                //CANCELADA_PASSAGEIRO
                // envia algum dado chave para colocar a tela em overview e mostrar mensagem q cancelou
                notifyScreenValue = HomeAct.MainScreenStage.OVERVIEW
            }
            //DEFAULT NOTIFICATIONS
            else -> {

            }
        }

        if (notifyScreenValue != null) {

            intent!!.putExtra("notifyScreen", notifyScreenValue)
            intentBroadcast.putExtra("notifyScreen", notifyScreenValue)
        }

        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        broadcastManager.sendBroadcast(intentBroadcast)


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