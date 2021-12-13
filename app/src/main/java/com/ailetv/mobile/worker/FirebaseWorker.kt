package com.ailetv.mobile.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ailetv.mobile.data.model.request.FirebaseTokenRequest
import com.ailetv.mobile.data.networking.api.AileTvApiClient
import com.ailetv.mobile.data.repo.AuthRepo
import com.ailetv.mobile.manager.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirebaseWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    val repo = AuthRepo(AileTvApiClient.provideAuthApi())

    init {
        SessionManager.init(applicationContext)
    }

    override suspend fun doWork() = withContext(Dispatchers.IO) {
        if (SessionManager.loggedIn) {
            val token = inputData.getString(ARGS_TOKEN)

            repo.firebaseToken(FirebaseTokenRequest(token))
        }

        Result.success()
    }

    companion object {
        const val ARGS_TOKEN = "token"
    }
}