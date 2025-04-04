package woowacourse.omok.data.db

object BoardContract {
    const val TABLE_NAME = "board"

    const val COLUMN_NAME_ROW = "row"
    const val COLUMN_NAME_COL = "column"
    const val COLUMN_NAME_STONE_COLOR = "stoneColor"

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "$COLUMN_NAME_ROW INTEGER," +
            "$COLUMN_NAME_COL INTEGER," +
            "$COLUMN_NAME_STONE_COLOR TEXT," +
            "primary key($COLUMN_NAME_ROW, $COLUMN_NAME_COL))"

    const val SQL_DELETE_ENTRIES = "DELETE FROM $TABLE_NAME"
}
