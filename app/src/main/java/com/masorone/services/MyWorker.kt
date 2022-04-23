package com.masorone.services

import android.content.Context
import android.util.Log
import androidx.work.*

class MyWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        log("doWork")
        val page = workerParams.inputData.getInt(PAGE, 0)
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("Timer $i : $page")
        }
        return Result.success()
    }

    private fun log(message: String) =
        Log.d("SERVICE_TAG", "${javaClass.simpleName}: $message")

    companion object {

        private const val PAGE = "page"
        const val WORK_NAME = "work_name"

        fun makeRequest(page: Int) = OneTimeWorkRequestBuilder<MyWorker>().apply {
            setInputData(workDataOf(PAGE to page))
            setConstraints(makeConstraints())
        }.build()

        private fun makeConstraints() = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }
}