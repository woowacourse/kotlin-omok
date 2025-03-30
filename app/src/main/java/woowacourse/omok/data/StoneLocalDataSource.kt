package woowacourse.omok.data

import android.database.sqlite.SQLiteOpenHelper

class StoneLocalDataSource(
    private val dbHelper: SQLiteOpenHelper,
) : StoneDataSource {
    override fun fetchAllStones(): List<StoneDao> {
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(OmokContract.SQL_FETCH_STONES, arrayOf())

        val stoneDaos = mutableListOf<StoneDao>()
        while (cursor.moveToNext()) {
            val stoneColorIndex = cursor.getColumnIndex(OmokContract.COLUMN_NAME_STONE_COLOR)
            val rowIndex = cursor.getColumnIndex(OmokContract.COLUMN_NAME_ROW)
            val colIndex = cursor.getColumnIndex(OmokContract.COLUMN_NAME_COL)

            val stoneColor = cursor.getString(stoneColorIndex)
            val row = cursor.getInt(rowIndex)
            val col = cursor.getInt(colIndex)
            stoneDaos.add(StoneDao(stoneColor, row, col))
        }
        return stoneDaos.toList()
    }

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

    override fun deleteAll() {
        val db = dbHelper.writableDatabase
        db.execSQL(OmokContract.SQL_DELETE_ENTRIES)
    }
}
