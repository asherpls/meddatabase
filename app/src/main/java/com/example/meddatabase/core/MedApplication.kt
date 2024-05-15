package com.example.meddatabase.core

import android.app.Application
import java.util.*
import kotlin.collections.ArrayList

class MedApplication : Application()  {
    companion object {
        lateinit var container: AppDataContainer
    }

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}