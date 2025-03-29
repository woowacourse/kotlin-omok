package woowacourse.omok.data

object StoneContract {
    const val TABLE_NAME = "stones"

    const val COLUMN_NAME_COLUMN = "y"
    const val COLUMN_NAME_ROW = "x"
    const val COLUMN_NAME_STONE_TYPE = "stone_type"

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_NAME_COLUMN INTEGER," +
            "$COLUMN_NAME_ROW INTEGER," +
            "$COLUMN_NAME_STONE_TYPE TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}
