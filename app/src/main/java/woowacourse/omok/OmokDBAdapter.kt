package woowacourse.omok

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import domain.stone.Color
import domain.stone.Stone

class OmokDBAdapter(db: OmokDBHelper) {

    private val writableDB: SQLiteDatabase = db.writableDatabase
    private val cursor = writableDB.query(
        OmokSpecification.TABLE_NAME,
        arrayOf(OmokSpecification.COLUMN_PLACED_INDEX, OmokSpecification.COLOR_STONE_COLOR),
        null,
        null,
        null,
        null,
        null
    )

    fun getStones(): MutableList<Stone> {
        val stones = mutableListOf<Stone>()

        with(cursor) {
            while (moveToNext()) {
                val index = getInt(getColumnIndexOrThrow(OmokSpecification.COLUMN_PLACED_INDEX))
                val color = getString(getColumnIndexOrThrow(OmokSpecification.COLOR_STONE_COLOR))
                stones.add(Stone(index.toPoint(), color.toColor()))

                Log.i("test", "$index $color")
            }
        }

        return stones
    }

    fun addStone(clickedIndex: Int, color: Color) {
        val values = ContentValues()
        values.put(OmokSpecification.COLUMN_PLACED_INDEX, clickedIndex)
        values.put(OmokSpecification.COLOR_STONE_COLOR, color.toName())

        writableDB.insert(OmokSpecification.TABLE_NAME, null, values)
    }

    fun deleteStones() {
        if (writableDB.isOpen) {
            writableDB.delete(OmokSpecification.TABLE_NAME, null, null)
        }
    }
}
