package woowacourse.omok.data.db

object BoardContract {
    const val TABLE_NAME = "board"

    const val COLUMN_NAME_GAME_ID = "id"
    const val COLUMN_NAME_X = "x"
    const val COLUMN_NAME_Y = "y"
    const val COLUMN_NAME_STATE = "state"
    private const val COLUMN_NAME_BOARD_ID = "board_id"

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_NAME_BOARD_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COLUMN_NAME_GAME_ID INTEGER," +
            "$COLUMN_NAME_X INTEGER," +
            "$COLUMN_NAME_Y INTEGER," +
            "$COLUMN_NAME_STATE TEXT," +
            "FOREIGN KEY($COLUMN_NAME_GAME_ID) REFERENCES ${GameContract.TABLE_NAME}(${GameContract.COLUMN_NAME_GAME_ID}) ON DELETE CASCADE)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}
