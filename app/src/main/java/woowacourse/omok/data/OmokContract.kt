package woowacourse.omok.data

object OmokContract {
    object GameState {
        const val TABLE_NAME = "game_state"
        const val COLUMN_ID = "id"
        const val COLUMN_X = "column"
        const val COLUMN_Y = "row"
        const val COLUMN_STONE_TYPE = "stoneType"

        const val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_X INTEGER NOT NULL,
                $COLUMN_Y INTEGER NOT NULL,
                $COLUMN_STONE_TYPE TEXT NOT NULL
            )
        """
    }

    object GameInfo {
        const val TABLE_NAME = "game_info"
        const val COLUMN_KEY = "key"
        const val COLUMN_VALUE = "value"

        const val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_KEY TEXT PRIMARY KEY,
                $COLUMN_VALUE TEXT NOT NULL
            )
        """
    }

    const val DATABASE_NAME = "omok_game.db"
    const val DATABASE_VERSION = 1
}
