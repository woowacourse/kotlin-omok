package woowacourse.omok

import android.app.Application
import woowacourse.omok.data.dao.OmokGameDao
import woowacourse.omok.data.dao.OmokGameDaoImpl
import woowacourse.omok.data.db.OmokDbHelper
import woowacourse.omok.ui.controller.OmokController
import woowacourse.omok.ui.view.InputView
import woowacourse.omok.ui.view.OutputView

class OmokApplication : Application() {
    lateinit var omokGameDao: OmokGameDao
        private set

    override fun onCreate() {
        super.onCreate()
        provideOmokGameDao()
    }

    private fun provideOmokGameDao() {
        val omokDbHelper = OmokDbHelper(this)
        omokGameDao = OmokGameDaoImpl(omokDbHelper)
    }
}

fun main() {
    val inputView = InputView()
    val outputView = OutputView()

    val controller = OmokController(inputView, outputView)
    controller.run()
}
