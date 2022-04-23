package ui.smartpro.appselect.activity

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import ui.smartpro.appselect.BuildConfig

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Core.initAppCore()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}