package woowacourse.omok.data

object GamesTableContract {
    const val TABLE_NAME = "Games"
    const val COLUMN_NAME_ID = "id"

    const val CREATE_TABLE = """
        CREATE TABLE $TABLE_NAME (
            $COLUMN_NAME_ID INTEGER PRIMARY KEY
        )
    """
}
