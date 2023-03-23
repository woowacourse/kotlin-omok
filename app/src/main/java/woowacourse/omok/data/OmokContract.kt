package woowacourse.omok.data

import android.provider.BaseColumns

object OmokContract : BaseColumns {
    object Board : BaseColumns {
        const val TABLE_NAME = "board"
        const val TABLE_COLUMN_Y = "y_position"
        const val TABLE_COLUMN_X = "x_position"
        const val TABLE_COLUMN_COLOR = "color"

        const val CREATE_BOARD_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "  ${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "  $TABLE_COLUMN_Y INTEGER," +
            "  $TABLE_COLUMN_X INTEGER," +
            "  $TABLE_COLUMN_COLOR INTEGER" +
            ");"
        const val DELETE_BOARD_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
