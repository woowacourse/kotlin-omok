package woowacourse.omok.db

import android.content.Context
import woowacourse.omok.db.DatabaseHelper.Companion.CURRENT_TURN
import woowacourse.omok.model.stone.StoneColor

class TurnDaoImpl(context: Context) : TurnDao {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    override fun saveTurn(color: StoneColor) {
        val db = dbHelper.writableDatabase
        deleteTurn()
        db.execSQL(
            "INSERT INTO $CURRENT_TURN (id, color) VALUES (1, ?)",
            arrayOf(color.name),
        )
        db.close()
    }

    override fun getLastTurn(): StoneColor {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT color FROM $CURRENT_TURN WHERE id = 1", null)
        var color = StoneColor.BLACK
        if (cursor.moveToFirst()) {
            color = StoneColor.valueOf(cursor.getString(0))
        }
        cursor.close()
        db.close()
        return color
    }

    override fun deleteTurn() {
        val db = dbHelper.writableDatabase
        db.execSQL("DELETE FROM $CURRENT_TURN")
    }
}
