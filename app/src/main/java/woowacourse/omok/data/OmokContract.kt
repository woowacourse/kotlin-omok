package woowacourse.omok.data

import android.provider.BaseColumns

object OmokContract {
    const val TABLE_NAME = "stones"

    const val COLUMN_NAME_COLOR = "color"
    const val COLUMN_NAME_ROW = "row"
    const val COLUMN_NAME_COLUMN = "column"
    const val COLUMN_NAME_ID = BaseColumns._ID

    const val SQL_CREATE_STONES =
        "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_NAME_ID INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_COLOR TEXT," +
            "$COLUMN_NAME_ROW INTEGER," +
            "$COLUMN_NAME_COLUMN INTEGER)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}
