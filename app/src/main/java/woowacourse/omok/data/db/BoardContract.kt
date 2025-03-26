package woowacourse.omok.data.db

import android.provider.BaseColumns

object BoardContract {
    const val TABLE_NAME = "board"

    const val COLUMN_NAME_X = "x"
    const val COLUMN_NAME_Y = "y"
    const val COLUMN_NAME_STATE = "state"
    private const val COLUMN_NAME_ID = BaseColumns._ID

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_NAME_ID INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_X INTEGER," +
            "$COLUMN_NAME_Y INTEGER," +
            "$COLUMN_NAME_STATE TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}
