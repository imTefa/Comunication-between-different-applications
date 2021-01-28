package com.example.receiver.bg.server

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.receiver.R
import com.example.receiver.bg.events.MessageEvent
import com.example.receiver.bg.events.ResponseEvent
import com.example.receiver.bg.reveivers.NewUserReceiver
import com.example.receiver.ui.main.MainActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class ServerService : Service() {

    private val TAG = ServerService::class.simpleName
    private val NOTIFICATION_ID = 1

    lateinit var serverThread: ServerThread

    override fun onCreate() {
        super.onCreate()
        serverThread = ServerThread()
        serverThread.start()

        EventBus.getDefault().register(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if ("ACTION_STOP_SERVICE" == intent?.action) {
            stopSelf();
        }

        createChannel(
            getString(R.string.server_notification_channel_id),
            getString(R.string.server_notification_channel_name)
        )
        startForeground(NOTIFICATION_ID, createNotification())
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
        if (serverThread.isAlive)
            serverThread.interrupt()

        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onMessageEvent(event: MessageEvent) {
        Intent(baseContext, NewUserReceiver::class.java).also { intent ->
            intent.putExtra("data", event.message)
            baseContext.sendBroadcast(intent)
        }
    }

    @Subscribe
    fun onResponseEvent(event: ResponseEvent) {
        serverThread.reply(event.response)
    }


    private fun createNotification(): Notification {

        val contentIntent = Intent(applicationContext, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val stopIntent = Intent(this, ServerService::class.java)
        stopIntent.action = "ACTION_STOP_SERVICE"

        val stopPendingIntent = PendingIntent.getService(
            this, 0, stopIntent, PendingIntent.FLAG_CANCEL_CURRENT
        )

        return NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.server_notification_channel_id)
        )
            .setContentTitle(getString(R.string.server_notification_title))
            .setContentText(getString(R.string.server_notification_description))
            .setContentIntent(contentPendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_stop_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSound(null)
            .addAction(R.drawable.ic_baseline_stop_24, "Stop", stopPendingIntent)
            .build()
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationChannel.description =
                getString(R.string.server_notification_channel_description)

            val notificationManager = getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}