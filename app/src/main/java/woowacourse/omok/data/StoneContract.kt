package woowacourse.omok.data

import android.provider.BaseColumns

object StoneContract {
    const val TABLE_NAME = "stones"

    const val COLUMN_GAME_ID = "game_id"
    const val COLUMN_NAME_COLUMN = "y"
    const val COLUMN_NAME_ROW = "x"
    const val COLUMN_NAME_STONE_TYPE = "stone_type"
    private const val COLUMN_NAME_ID = BaseColumns._ID

    const val SQL_CREATE_STONES =
        "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_NAME_ID INTEGER PRIMARY KEY," +
            "$COLUMN_GAME_ID INTEGER," +
            "$COLUMN_NAME_COLUMN INTEGER," +
            "$COLUMN_NAME_ROW INTEGER," +
            "$COLUMN_NAME_STONE_TYPE TEXT)"

    const val SQL_DELETE_STONES = "DROP TABLE IF EXISTS $TABLE_NAME"
}
