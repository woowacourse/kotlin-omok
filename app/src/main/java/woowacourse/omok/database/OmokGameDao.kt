package woowacourse.omok.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import woowacourse.omok.model.StoneColor

class OmokGameDao(
    context: Context,
) {
    private val dbHelper = DbHelper(context)

    fun saveStone(
        x: Int,
        y: Int,
        turn: StoneColor,
    ) {
        val db = dbHelper.writableDatabase

        val values =
            ContentValues().apply {
                put(OmokGameContract.COLUMN_NAME_X, x)
                put(OmokGameContract.COLUMN_NAME_Y, y)
                put(OmokGameContract.COLUMN_NAME_TURN, turn.toString())
            }

        val newRowId = db.insert(OmokGameContract.TABLE_NAME, null, values)

        if (newRowId == -1L) {
            Log.e(TAG, "insert failed")
        } else {
            Log.d(TAG, "insert success: $newRowId")
        }
        db.close()
    }

    fun loadGameState(): List<SavedStone> {
        val db = dbHelper.readableDatabase
        val result = mutableListOf<SavedStone>()

        val cursor: Cursor =
            db.query(
                OmokGameContract.TABLE_NAME,
                arrayOf(
                    OmokGameContract.COLUMN_NAME_X,
                    OmokGameContract.COLUMN_NAME_Y,
                    OmokGameContract.COLUMN_NAME_TURN,
                ),
                null,
                null,
                null,
                null,
                null,
            )

        with(cursor) {
            while (moveToNext()) {
                val x = getInt(getColumnIndexOrThrow(OmokGameContract.COLUMN_NAME_X))
                val y = getInt(getColumnIndexOrThrow(OmokGameContract.COLUMN_NAME_Y))
                val colorString =
                    getString(getColumnIndexOrThrow(OmokGameContract.COLUMN_NAME_TURN))

                val color =
                    when (colorString) {
                        "BLACK" -> StoneColor.BLACK
                        "WHITE" -> StoneColor.WHITE
                        else -> throw IllegalArgumentException("Invalid color: $colorString")
                    }

                result.add(SavedStone(x, y, color))
            }
        }

        cursor.close()
        db.close()
        return result
    }

    fun clearGameData() {
        val db = dbHelper.writableDatabase
        db.delete(OmokGameContract.TABLE_NAME, null, null)
        db.close()
    }

    fun close() {
        dbHelper.close()
    }

    companion object {
        private const val TAG = "OmokGameDao"
    }
}
