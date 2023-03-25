package woowacourse.omok

import android.app.Application
import android.content.SharedPreferences

class Omok : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        sharedPref = getSharedPreferences("Omok", MODE_PRIVATE)
    }

    companion object {
        lateinit var instance: Omok
            private set
        lateinit var sharedPref: SharedPreferences
            private set
    }
}
