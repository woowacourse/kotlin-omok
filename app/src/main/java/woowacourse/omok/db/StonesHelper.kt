package woowacourse.omok.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StonesHelper(
    context: Context?
) : SQLiteOpenHelper(context, db_name, null, db_version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${StoneConstract.TABLE_NAME}(" +
                "  ${StoneConstract.TABLE_COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  ${StoneConstract.TABLE_COLUMN_ROOM_NUMBER} int," +
                "  ${StoneConstract.TABLE_COLUMN_X} int," +
                "  ${StoneConstract.TABLE_COLUMN_Y} int," +
                "  ${StoneConstract.TABLE_COLUMN_COLOR} int" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${StoneConstract.TABLE_NAME}")
        onCreate(db)
    }

    companion object {
        private const val db_name = "omok_db"
        private const val db_version = 6
    }
}