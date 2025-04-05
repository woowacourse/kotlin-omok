package woowacourse.omok.database

import android.provider.BaseColumns

object OmokGameContract {
    const val TABLE_NAME = "moves"
    const val COLUMN_NAME_ID = BaseColumns._ID
    const val COLUMN_NAME_X = "x"
    const val COLUMN_NAME_Y = "y"
    const val COLUMN_NAME_TURN = "color"

    const val SQL_CREATE_TABLE =
        "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_NAME_ID INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_X INTEGER NOT NULL, " +
            "$COLUMN_NAME_Y INTEGER NOT NULL, " +
            "$COLUMN_NAME_TURN TEXT NOT NULL " +
            ")"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}
