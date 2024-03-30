package woowacourse.omok.db

import android.provider.BaseColumns

object OmokContract {
    const val DATABASE_NAME = "OmokGame.db"
    const val DATABASE_VERSION = 1

    object GameRecordEntry : BaseColumns {
        const val TABLE_NAME = "GameRecord"
        const val COLUMN_X = "x"
        const val COLUMN_Y = "y"
        const val COLUMN_COLOR = "color"

        const val SQL_CREATE_TABLE =
            "" +
                "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "$COLUMN_X INTEGER," +
                "$COLUMN_Y INTEGER," +
                "$COLUMN_COLOR TEXT)"

        const val SQL_SELECT_ALL = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_X ASC, $COLUMN_Y ASC"

        const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
