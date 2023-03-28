package woowacourse.omok

import android.app.Application

class Omok : Application() {
    override fun onCreate() {
        super.onCreate()
        Storage.init(this)
    }
}
