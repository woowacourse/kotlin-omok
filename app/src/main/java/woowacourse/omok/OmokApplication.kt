package woowacourse.omok

import android.app.Application

class OmokApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        controller = AndroidController()
    }

    companion object {
        lateinit var controller: AndroidController
    }
}
