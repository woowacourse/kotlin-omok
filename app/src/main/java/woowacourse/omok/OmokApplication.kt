package woowacourse.omok

import android.app.Application
import woowacourse.omok.domain.repository.OmokRepository
import woowacourse.omok.ui.controller.OmokController
import woowacourse.omok.ui.view.InputView
import woowacourse.omok.ui.view.OutputView

class OmokApplication : Application() {
    lateinit var omokRepository: OmokRepository
        private set

    override fun onCreate() {
        super.onCreate()
        omokRepository = OmokRepository(applicationContext)
    }
}

fun main() {
    val inputView = InputView()
    val outputView = OutputView()

    val controller = OmokController(inputView, outputView)
    controller.run()
}
