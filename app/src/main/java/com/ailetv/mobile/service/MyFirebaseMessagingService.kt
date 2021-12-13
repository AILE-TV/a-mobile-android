package com.ailetv.mobile.service


import android.content.Context
import androidx.work.*
import com.ailetv.mobile.utils.NotificationUtils
import com.ailetv.mobile.worker.FirebaseWorker
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import toBundle

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            Timber.e("Message data payload: ${remoteMessage.data}")
        }

        remoteMessage.notification?.let {
            NotificationUtils.sendNotification(
                applicationContext,
                remoteMessage.data.toBundle(), it.title, it.body
            )
        }
    }

    override fun onNewToken(token: String) {
        sendRegistrationToServer(applicationContext, token)
    }

    companion object {
        fun sendRegistrationToServer(context: Context?, token: String?) {
            if (context != null && token != null) {
                Timber.e(token.toString())
                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

                val work = OneTimeWorkRequest.Builder(FirebaseWorker::class.java)
                    .setInputData(workDataOf(Pair(FirebaseWorker.ARGS_TOKEN, token)))
                    .setConstraints(constraints)
                    .build()

                WorkManager.getInstance(context)
                    .beginWith(work)
                    .enqueue()
            }
        }
    }
}