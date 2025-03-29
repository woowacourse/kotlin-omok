package woowacourse.omok.data

import android.database.sqlite.SQLiteOpenHelper

class StoneLocalDataSource(
    private val dbHelper: SQLiteOpenHelper,
) : StoneDataSource {
    override fun insert(stoneDao: StoneDao) {
        val db = dbHelper.writableDatabase
        val data =
            arrayOf(
                stoneDao.stoneColor,
                stoneDao.row.value,
                stoneDao.col.value,
            )
        db.execSQL(OmokContract.SQL_INSERT_STONE, data)
    }
}
