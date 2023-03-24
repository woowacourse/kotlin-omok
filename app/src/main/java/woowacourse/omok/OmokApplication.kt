package woowacourse.omok

import android.app.Application

class OmokApplication : Application() {

    companion object {
        var controller: AndroidController = AndroidController()
    }
}
