package woowacourse.omok.database

import android.provider.BaseColumns

object BoardContract {
    const val TABLE_NAME_BOARD = "board"

    const val COLUMN_NAME_COLOR = "color"
    const val COLUMN_NAME_POSITION_ROW = "row"
    const val COLUMN_NAME_POSITION_COLUMN = "column"
    const val COLUMN_NAME_ID = BaseColumns._ID

    const val SQL_CREATE_BOARD_ENTERIES =
        "CREATE TABLE $TABLE_NAME_BOARD (" +
            "$COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COLUMN_NAME_COLOR TEXT," +
            "$COLUMN_NAME_POSITION_ROW INTEGER, " +
            "$COLUMN_NAME_POSITION_COLUMN INTEGER)"

    const val SQL_DELETE_BOARD_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME_BOARD"
}
