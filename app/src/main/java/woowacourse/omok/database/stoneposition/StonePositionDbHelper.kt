package woowacourse.omok.database.stoneposition

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StonePositionDbHelper(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
                CREATE TABLE ${StonePositionConstract.TABLE_NAME} (
                    ${StonePositionConstract.TABLE_COLUMN_ROW} INTEGER, 
                    ${StonePositionConstract.TABLE_COLUMN_COLUMN} INTEGER
                )
            """.trimIndent()
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val DATABASE_NAME = "otter.db"
    }
}
