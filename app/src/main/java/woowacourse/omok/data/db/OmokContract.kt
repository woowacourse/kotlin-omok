package woowacourse.omok.data.db

object OmokContract {
    const val TABLE_GAME_STATE = "game_state"

    const val COLUMN_POSITION_ROW = "row"
    const val COLUMN_POSITION_COL = "col"
    const val COLUMN_POSITION_STATE = "state"
    const val COLUMN_LAST_TURN = "last_turn"
    const val COLUMN_HOST = "host"
    const val COLUMN_GAME_ID = "game_id"

    const val SQL_CREATE_GAME_STATE: String =
        """
        CREATE TABLE $TABLE_GAME_STATE (
            $COLUMN_GAME_ID INTEGER,
            $COLUMN_POSITION_ROW INTEGER,
            $COLUMN_POSITION_COL INTEGER,
            $COLUMN_POSITION_STATE TEXT,
            $COLUMN_LAST_TURN TEXT,
            $COLUMN_HOST TEXT,
            PRIMARY KEY ($COLUMN_GAME_ID, $COLUMN_POSITION_ROW, $COLUMN_POSITION_COL)
        )
        """

    const val SQL_SELECT_ALL_GAMES: String =
        """
        SELECT $COLUMN_GAME_ID, 
               $COLUMN_POSITION_ROW, 
               $COLUMN_POSITION_COL,
               $COLUMN_POSITION_STATE, 
               $COLUMN_LAST_TURN, 
               $COLUMN_HOST
        FROM $TABLE_GAME_STATE
        ORDER BY $COLUMN_GAME_ID
        """

    const val SQL_DELETE_GAME_STATE: String = "DROP TABLE IF EXISTS $TABLE_GAME_STATE"

    const val SQL_SELECT_MAX_ID: String = "SELECT MAX($COLUMN_GAME_ID) FROM $TABLE_GAME_STATE"
}
