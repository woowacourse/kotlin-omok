package woowacourse.omok.data

import android.provider.BaseColumns

object OmokContract {
    const val TABLE_NAME = "boards"

    const val COLUMN_NAME_ID = BaseColumns._ID
    const val COLUMN_NAME_STONE_COLOR = "stone_color"
    const val COLUMN_NAME_ROW = "row"
    const val COLUMN_NAME_COL = "column"

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_NAME_ID INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_STONE_COLOR TEXT," +
            "$COLUMN_NAME_ROW INTEGER," +
            "$COLUMN_NAME_COL INTEGER);"

    const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    const val SQL_FETCH_STONES =
        """ SELECT *
            FROM $TABLE_NAME
        """
}
