package woowacourse.omok.database

object OmokTurnContract {
    const val OMOK_TURN_DATABASE_NAME = "omok_turn.db"
    const val OMOK_TURN_DATABASE_VERSION = 1

    const val TABLE_NAME = "omok_turn"
    const val COLUMN_ID = "id"
    const val COLUMN_POSITION_ROW = "row"
    const val COLUMN_POSITION_COLUMN = "column"
    const val COLUMN_STONE_COLOR = "stone"

    val ALL_COLUMNS =
        arrayOf(
            COLUMN_ID,
            COLUMN_POSITION_ROW,
            COLUMN_POSITION_COLUMN,
            COLUMN_STONE_COLOR,
        )

    const val CREATE_OMOK_TURN_TABLE = """
        CREATE TABLE IF NOT EXISTS  $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_POSITION_ROW INTEGER NOT NULL,
            $COLUMN_POSITION_COLUMN INTEGER NOT NULL,
            $COLUMN_STONE_COLOR TEXT
        )
    """

    const val DROP_OMOK_TURN_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}
