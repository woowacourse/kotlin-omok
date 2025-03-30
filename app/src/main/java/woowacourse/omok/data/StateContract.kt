package woowacourse.omok.data

object StateContract {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "State.db"
    const val TABLE_NAME = "game_state"

    const val COLUMN_ID = "id"
    const val COLUMN_STATE = "state"
    const val BLACK_STONES_TABLE = "black_stones"
    const val WHITE_STONES_TABLE = "white_stones"

    const val COLUMN_X = "x"
    const val COLUMN_Y = "y"

    const val SQL_CREATE_STATE =
        "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_ID INTEGER PRIMARY KEY," +
            "$COLUMN_STATE TEXT)"

    const val SQL_CREATE_BLACK_STONES =
        "CREATE TABLE $BLACK_STONES_TABLE (" +
            "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COLUMN_X INTEGER," +
            "$COLUMN_Y INTEGER)"

    const val SQL_CREATE_WHITE_STONES =
        "CREATE TABLE $WHITE_STONES_TABLE (" +
            "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COLUMN_X INTEGER," +
            "$COLUMN_Y INTEGER)"

    const val SQL_DELETE_STATE = "DROP TABLE IF EXISTS $TABLE_NAME"
    const val SQL_DELETE_BLACK_STONES = "DROP TABLE IF EXISTS $BLACK_STONES_TABLE"
    const val SQL_DELETE_WHITE_STONES = "DROP TABLE IF EXISTS $WHITE_STONES_TABLE"
}
