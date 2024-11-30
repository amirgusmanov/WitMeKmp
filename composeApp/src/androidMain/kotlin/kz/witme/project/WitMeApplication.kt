package kz.witme.project

import android.app.Application
import kz.witme.project.di.initKoin
import org.koin.android.ext.koin.androidContext

class WitMeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@WitMeApplication)
        }
    }
}