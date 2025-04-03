package woowacourse

import android.app.Application
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.data.GameDao
import woowacourse.omok.data.GameRepositoryImpl
import woowacourse.omok.data.OmokDatabaseHelper
import woowacourse.omok.data.StoneDao
import woowacourse.omok.data.StoneRepositoryImpl
import woowacourse.omok.domain.repository.GameRepository
import woowacourse.omok.domain.repository.StoneRepository

class App : Application() {
    private lateinit var dbHelper: SQLiteOpenHelper
    lateinit var stoneRepository: StoneRepository
    lateinit var gameRepository: GameRepository

    override fun onCreate() {
        super.onCreate()
        dbHelper = OmokDatabaseHelper(this)
        stoneRepository = StoneRepositoryImpl(StoneDao(dbHelper = dbHelper))
        gameRepository = GameRepositoryImpl(GameDao(dbHelper = dbHelper))
    }
}
