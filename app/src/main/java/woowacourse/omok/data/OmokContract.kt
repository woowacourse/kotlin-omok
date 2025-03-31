package woowacourse.omok.data

object OmokContract {
    const val TABLE_NAME = "omok"

    const val COLUMN_NAME_X = "x"
    const val COLUMN_NAME_Y = "y"
    const val COLUMN_NAME_COLOR = "stone"
    const val COLUMN_NAME_ID = "id"

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${COLUMN_NAME_ID} INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_X INTEGER," +
            "$COLUMN_NAME_Y INTEGER," +
            "$COLUMN_NAME_COLOR TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}
