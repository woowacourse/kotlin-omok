package woowacourse.omok

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import domain.stone.Color
import domain.stone.Stone

class OmokDBAdapter(db: OmokDBHelper) {

    private val writableDB: SQLiteDatabase = db.writableDatabase
    private val cursor = writableDB.query(
        OmokContract.TABLE_NAME,
        arrayOf(OmokContract.COLUMN_PLACED_INDEX, OmokContract.COLUMN_STONE_COLOR),
        null,
        null,
        null,
        null,
        null
    )


    fun getStones(): List<Stone> {
        val stones = mutableListOf<Stone>()

        with(cursor) {
            while (moveToNext()) {
                val index = getInt(getColumnIndexOrThrow(OmokContract.COLUMN_PLACED_INDEX))
                val color = getString(getColumnIndexOrThrow(OmokContract.COLUMN_STONE_COLOR))
                stones.add(Stone(index.toPoint(), color.descriptionToColor()))
            }
        }

        return stones.toList()
    }

    fun addStone(clickedIndex: Int, color: Color) {
        val values = ContentValues()

        values.put(OmokContract.COLUMN_PLACED_INDEX, clickedIndex)
        values.put(OmokContract.COLUMN_STONE_COLOR, color.toName())
        writableDB.insert(OmokContract.TABLE_NAME, null, values)
    }

    fun deleteStones() {
        if (writableDB.isOpen) {
            writableDB.delete(OmokContract.TABLE_NAME, null, null)
        }
    }
}
