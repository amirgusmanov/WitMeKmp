package kz.witme.project

import android.app.Application
import kz.witme.project.di.initKoin
import kz.witme.project.navigation.NavRegistry
import org.koin.android.ext.koin.androidContext

class WitMeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        NavRegistry.registerScreens()
        initKoin {
            androidContext(this@WitMeApplication)
        }
    }
}