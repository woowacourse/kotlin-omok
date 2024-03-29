package woowacourse.omok.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "omok.db"
        private const val DATABASE_VERSION = 4
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE omok_coordinates (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "x_column INTEGER," +
                "y_row INTEGER" +
                ");"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS omok_coordinates")
        onCreate(db)
    }
}
