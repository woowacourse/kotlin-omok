package woowacourse.omok.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_POINTS_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_POINTS_TABLE_QUERY)
    }

    companion object {
        private const val DB_NAME: String = "omok.db"
        private const val DB_VERSION: Int = 1
        const val POINT_TABLE_NAME: String = "points"
        const val KEY_BOARD_INDEX = "board_index"
        const val KEY_GO_STONE_COLOR = "go_stone_color"
        const val KEY_COORDINATE_X = "x"
        const val KEY_COORDINATE_Y = "y"

        private const val CREATE_POINTS_TABLE_QUERY = "CREATE TABLE $POINT_TABLE_NAME (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$KEY_BOARD_INDEX," +
            "$KEY_GO_STONE_COLOR INTEGER NOT NULL," +
            "$KEY_COORDINATE_X INTEGER NOT NULL," +
            "$KEY_COORDINATE_Y INTEGER NOT NULL" +
            ");"
        private const val DROP_POINTS_TABLE_QUERY = "DROP TABLE IF EXISTS $POINT_TABLE_NAME"
    }
}
