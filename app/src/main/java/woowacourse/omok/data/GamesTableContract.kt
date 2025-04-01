package woowacourse.omok.data

object GamesTableContract {
    const val TABLE_NAME = "Games"
    const val COLUMN_NAME_ID = "id"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_STATUS = "isFinished"

    const val VALUE_GAME_STATUS_PLAYING = 0
    const val VALUE_GAME_STATUS_FINISHED = 1

    const val CREATE_TABLE = """
        CREATE TABLE $TABLE_NAME (
            $COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_NAME_TITLE TEXT,
            $COLUMN_NAME_STATUS INTEGER
    )
    """
}
