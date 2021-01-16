package cl.gerardomascayano.tdmadmin

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TDMApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            debugConfig()
        } else {
            releaseConfig()
        }

        Firebase.messaging.subscribeToTopic(getString(R.string.fcm_topic_order)).addOnCompleteListener {
            if (it.isSuccessful) Timber.d("Suscripción exitosa") else Timber.e(it.exception)
        }

//        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
//            Timber.d(task.result)
//        }
    }

    private fun debugConfig() {
        Timber.plant(Timber.DebugTree())
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
    }

    private fun releaseConfig() {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }
}