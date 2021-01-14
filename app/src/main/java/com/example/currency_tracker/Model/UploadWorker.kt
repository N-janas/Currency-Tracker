package com.example.currency_tracker.Model

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadWorker(appContext: Context, workerParams: WorkerParameters):
        Worker(appContext, workerParams){
    override fun doWork(): Result {

        // do work

        return Result.failure()
    }
}