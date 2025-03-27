package woowacourse.omok.data.db

object GameContract {
    const val TABLE_NAME = "game"

    const val COLUMN_NAME_GAME_NAME = "game_name"
    const val COLUMN_NAME_GAME_ID = "game_id"

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_NAME_GAME_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COLUMN_NAME_GAME_NAME TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}
