package woowacourse.omok

import android.app.Application
import woowacourse.omok.data.OmokDao
import woowacourse.omok.data.OmokDaoImpl
import woowacourse.omok.data.OmokDatabaseHelper

class OmokApplication : Application() {
    lateinit var omokDao: OmokDao
        private set

    override fun onCreate() {
        super.onCreate()
        val dbHelper = OmokDatabaseHelper(this)
        val database = dbHelper.writableDatabase
        omokDao = OmokDaoImpl(database)
    }
}
