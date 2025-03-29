package woowacourse.omok.domain.db

import android.provider.BaseColumns

object OmokContract {
    const val TABLE_NAME = "omok"

    const val STONE_COLOR = "color"
    const val POSITION_ROW = "row"
    const val POSITION_COLUMN = "column"
    const val COLUMN_NAME_ID = BaseColumns._ID

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
            "${COLUMN_NAME_ID} INTEGER PRIMARY KEY," +
            "$STONE_COLOR TEXT," +
            "$POSITION_ROW INTEGER," +
            "$POSITION_COLUMN INTEGER)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}
