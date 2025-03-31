package woowacourse.omok.data.db

object OmokContract {
    const val TABLE_GAME_STATE = "game_state"

    const val COLUMN_GAME_ID = "game_id"
    const val COLUMN_POSITION_ROW = "row"
    const val COLUMN_POSITION_COL = "col"
    const val COLUMN_POSITION_STATE = "state"
    const val COLUMN_LAST_TURN = "last_turn"

    const val SQL_CREATE_GAME_STATE: String =
        """
        CREATE TABLE $TABLE_GAME_STATE (
            $COLUMN_GAME_ID INTEGER,
            $COLUMN_POSITION_ROW INTEGER,
            $COLUMN_POSITION_COL INTEGER,
            $COLUMN_POSITION_STATE TEXT,
            $COLUMN_LAST_TURN TEXT
        )
        """

    const val SQL_DELETE_GAME_STATE: String = "DROP TABLE IF EXISTS $TABLE_GAME_STATE"
}
