package woowacourse.omok

import android.app.Application
import woowacourse.omok.domain.repository.OmokRepository

class OmokApplication : Application() {
    lateinit var omokRepository: OmokRepository
        private set

    override fun onCreate() {
        super.onCreate()
        omokRepository = OmokRepository(applicationContext)
    }
}
