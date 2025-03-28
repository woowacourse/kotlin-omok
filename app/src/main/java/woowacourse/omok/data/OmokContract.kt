package woowacourse.omok.data

object OmokContract {
    const val TABLE_GAME = "game"
    const val TABLE_BOARD = "board"

    const val COLUMN_LAST_TURN = "last_turn"

    const val COLUMN_POSITION_ROW = "row"
    const val COLUMN_POSITION_COL = "col"
    const val COLUMN_POSITION_STATE = "state"

    const val SQL_CREATE_GAME =
        "CREATE TABLE $TABLE_GAME (" +
            "$COLUMN_LAST_TURN TEXT)"

    const val SQL_CREATE_BOARD =
        "CREATE TABLE $TABLE_BOARD (" +
            "$COLUMN_POSITION_ROW INTEGER," +
            "$COLUMN_POSITION_COL INTEGER," +
            "$COLUMN_POSITION_STATE TEXT)"

    const val SQL_DELETE_GAME = "DROP TABLE IF EXISTS $TABLE_GAME"
    const val SQL_DELETE_BOARD = "DROP TABLE IF EXISTS $TABLE_BOARD"
}
