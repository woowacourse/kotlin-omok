package woowacourse.omok.database.user

object UserContract {
    const val USER_DATABASE_NAME = "omok_user.db"
    const val USER_DATABASE_VERSION = 1

    const val TABLE_NAME = "omok_user"
    const val COLUMN_ID = "id"
    const val COLUMN_NAME = "name"
    const val COLUMN_WIN = "winCount"
    const val COLUMN_LOSE = "loseCount"

    val ALL_COLUMNS =
        arrayOf(
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_WIN,
            COLUMN_LOSE,
        )

    const val CREATE_USER_TABLE = """
        CREATE TABLE IF NOT EXISTS  $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_NAME TEXT,
            $COLUMN_WIN INTEGER NOT NULL,
            $COLUMN_LOSE INTEGER NOT NULL
        )
    """
    const val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}
