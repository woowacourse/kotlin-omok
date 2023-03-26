package woowacourse.omok.data

import android.provider.BaseColumns

object OmokContract : BaseColumns {
    object Board : BaseColumns {
        const val TABLE_NAME = "board"
        const val TABLE_COLUMN_ID = "boardId"
        const val TABLE_COLUMN_Y = "y_position"
        const val TABLE_COLUMN_X = "x_position"
        const val TABLE_COLUMN_COLOR = "color"

        const val CREATE_BOARD_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "  $TABLE_COLUMN_ID INTEGER," +
            "  $TABLE_COLUMN_Y INTEGER," +
            "  $TABLE_COLUMN_X INTEGER," +
            "  $TABLE_COLUMN_COLOR INTEGER," +
            "  FOREIGN KEY($TABLE_COLUMN_ID) REFERENCES ${Room.TABLE_NAME}(${BaseColumns._ID}) ON UPDATE CASCADE ON DELETE CASCADE" +
            ");"
        const val DELETE_BOARD_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object Room : BaseColumns {
        const val TABLE_NAME = "room"
        const val TABLE_COLUMN_TITLE = "roomTitle"
        const val TABLE_COLUMN_FIRST_USER_ID = "first_user"
        const val TABLE_COLUMN_SECOND_USER_ID = "second_user"

        const val CREATE_ROOM_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "  ${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "  $TABLE_COLUMN_TITLE TEXT," +
            "  $TABLE_COLUMN_FIRST_USER_ID INTEGER," +
            "  $TABLE_COLUMN_SECOND_USER_ID INTEGER," +
            "  FOREIGN KEY($TABLE_COLUMN_FIRST_USER_ID) REFERENCES ${User.TABLE_NAME}(${BaseColumns._ID}) ON UPDATE CASCADE ON DELETE CASCADE," +
            "  FOREIGN KEY($TABLE_COLUMN_SECOND_USER_ID) REFERENCES ${User.TABLE_NAME}(${BaseColumns._ID}) ON UPDATE CASCADE ON DELETE CASCADE" +
            ");"
        const val DELETE_ROOM_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object User : BaseColumns {
        const val TABLE_NAME = "user"
        const val TABLE_COLUMN_USER_NAME = "name"
        const val TABLE_COLUMN_WIN = "win"
        const val TABLE_COLUMN_LOSE = "lose"

        const val CREATE_USER_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "  ${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "  $TABLE_COLUMN_USER_NAME TEXT," +
            "  $TABLE_COLUMN_WIN INTEGER," +
            "  $TABLE_COLUMN_LOSE INTEGER" +
            ");"
        const val DELETE_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
