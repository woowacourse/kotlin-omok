package woowacourse.omok.data

object OmokContract {
    const val DATABASE_NAME = "OmokGame.db"
    const val DATABASE_VERSION = 1

    const val COLUMN_NAME_ID = "id"

    const val TABLE_NAME_GAMES = "Games"

    const val TABLE_NAME_BOARD = "Board"
    const val COLUMN_NAME_GAME_ID_FK = "game_id"
    const val COLUMN_NAME_X = "x"
    const val COLUMN_NAME_Y = "y"
    const val COLUMN_NAME_COLOR = "stone_color"

    const val CREATE_GAMES_TABLE = """
        CREATE TABLE $TABLE_NAME_GAMES (
            $COLUMN_NAME_ID INTEGER PRIMARY KEY
        )
    """

    const val CREATE_BOARD_TABLE = """
        CREATE TABLE $TABLE_NAME_BOARD (
            $COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_NAME_GAME_ID_FK INTEGER,
            $COLUMN_NAME_X INTEGER,
            $COLUMN_NAME_Y INTEGER,
            $COLUMN_NAME_COLOR TEXT,
            FOREIGN KEY ($COLUMN_NAME_GAME_ID_FK) REFERENCES $TABLE_NAME_GAMES($COLUMN_NAME_ID) ON DELETE CASCADE
        )
    """
}
