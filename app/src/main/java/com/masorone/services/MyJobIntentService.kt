package com.masorone.services

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class MyJobIntentService : JobIntentService() {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    override fun onHandleWork(intent: Intent) {
        log("onHandleWork")
        val page = intent.getIntExtra(PAGE, 0)
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("Timer $i : $page")
        }
    }

    private fun log(message: String) = Log.d("SERVICE_TAG", "${javaClass.simpleName}: $message")

    companion object {

        private const val PAGE = "page"
        private const val JOB_ID = 1

        fun enqueue(context: Context, page: Int) {
            enqueueWork(
                context,
                MyJobIntentService::class.java,
                JOB_ID,
                newIntent(context, page)
            )
        }

        private fun newIntent(context: Context, page: Int) = Intent(
            context,
            MyJobIntentService::class.java
        ).apply {
            putExtra(PAGE, page)
        }
    }
}