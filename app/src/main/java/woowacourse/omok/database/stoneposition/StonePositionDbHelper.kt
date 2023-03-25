package woowacourse.omok.database.stoneposition

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StonePositionDbHelper(
    context: Context?
) : SQLiteOpenHelper(context, "otter.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${StonePositionConstract.TABLE_NAME} (" +
                "position_row int," +
                "position_cal int" +
                ");"
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}
