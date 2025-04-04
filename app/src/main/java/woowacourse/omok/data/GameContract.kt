package woowacourse.omok.data

import android.provider.BaseColumns

object GameContract {
    const val TABLE_NAME = "games"

    const val COLUMN_NAME_NAME = "name"
    private const val COLUMN_NAME_ID = BaseColumns._ID

    const val SQL_CREATE_GAMES =
        "CREATE TABLE $TABLE_NAME (" +
            "$COLUMN_NAME_ID INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_NAME TEXT)"

    const val SQL_DELETE_GAMES = "DROP TABLE IF EXISTS $TABLE_NAME"
}
