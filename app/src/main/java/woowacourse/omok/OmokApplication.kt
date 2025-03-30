package woowacourse.omok

import android.app.Application
import woowacourse.omok.data.datasource.OmokGameLocalDataSourceImpl
import woowacourse.omok.domain.repository.OmokGameRepository
import woowacourse.omok.domain.repository.OmokGameRepositoryImpl
import woowacourse.omok.ui.controller.OmokController
import woowacourse.omok.ui.view.InputView
import woowacourse.omok.ui.view.OutputView

class OmokApplication : Application() {
    lateinit var omokGameRepository: OmokGameRepository
        private set

    override fun onCreate() {
        super.onCreate()
        provideOmokGameRepository()
    }

    private fun provideOmokGameRepository() {
        val gameDataSource = OmokGameLocalDataSourceImpl(applicationContext)
        omokGameRepository = OmokGameRepositoryImpl(gameDataSource)
    }
}

fun main() {
    val inputView = InputView()
    val outputView = OutputView()

    val controller = OmokController(inputView, outputView)
    controller.run()
}
