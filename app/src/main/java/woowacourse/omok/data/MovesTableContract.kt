package woowacourse.omok.data

object MovesTableContract {
    const val TABLE_NAME = "Moves"
    const val COLUMN_NAME_ID = "id"
    const val COLUMN_NAME_GAME_ID_FK = "game_id"
    const val COLUMN_NAME_X = "x"
    const val COLUMN_NAME_Y = "y"
    const val COLUMN_NAME_COLOR = "stone_color"

    const val CREATE_TABLE = """
        CREATE TABLE $TABLE_NAME (
            $COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_NAME_GAME_ID_FK INTEGER,
            $COLUMN_NAME_X INTEGER,
            $COLUMN_NAME_Y INTEGER,
            $COLUMN_NAME_COLOR TEXT,
            FOREIGN KEY ($COLUMN_NAME_GAME_ID_FK) REFERENCES ${GamesTableContract.TABLE_NAME}(${GamesTableContract.COLUMN_NAME_ID}) ON DELETE CASCADE
        )
    """
}
