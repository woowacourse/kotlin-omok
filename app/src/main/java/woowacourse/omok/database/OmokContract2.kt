package woowacourse.omok.database

import android.provider.BaseColumns

object OmokContract2 {
    const val TABLE_NAME = "omok"
    const val COLUMN_NAME_ID = BaseColumns._ID
    const val COLUMN_NAME_X = "x"
    const val COLUMN_NAME_Y = "y"
    const val COLUMN_NAME_COLOR = "color"
    const val COLUMN_NAME_ROOM_NAME = "room_name"

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_NAME_ID INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_X INTEGER," +
            "$COLUMN_NAME_Y INTEGER," +
            "$COLUMN_NAME_COLOR TEXT," +
            "$COLUMN_NAME_ROOM_NAME TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}
