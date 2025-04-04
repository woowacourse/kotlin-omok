package woowacourse.omok.data

import android.provider.BaseColumns

object OmokContract {
    const val TABLE_NAME = "omok"

    const val COLUMN_PLACE_ID = BaseColumns._ID
    const val COLUMN_ROOM_ID = "roomId"
    const val COLUMN_ROW_POSITION = "row"
    const val COLUMN_COL_POSITION = "col"
    const val COLUMN_STONE_COLOR = "stoneColor"

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_PLACE_ID INTEGER PRIMARY KEY," +
            "$COLUMN_ROOM_ID INTEGER," +
            "$COLUMN_ROW_POSITION INTEGER," +
            "$COLUMN_COL_POSITION INTEGER," +
            "$COLUMN_STONE_COLOR TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}
