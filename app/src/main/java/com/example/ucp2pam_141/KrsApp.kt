package com.example.ucp2pam_141

import android.app.Application
import com.example.ucp2pam_141.depedenciesinjection.ContainerApp

class KrsApp : Application() {
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()

        containerApp = ContainerApp(this)
    }
}