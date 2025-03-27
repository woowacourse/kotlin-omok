package woowacourse.omok

import android.provider.BaseColumns

object RoomContract {
    const val TABLE_NAME = "rooms"

    const val COLUMN_NAME_PLAYER = "nickname"
    const val COLUMN_NAME_STONE_COUNT = "stone_count"
    const val COLUMN_NAME_ID = BaseColumns._ID

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME (" +
                "${COLUMN_NAME_ID} INTEGER PRIMARY KEY," +
                "$COLUMN_NAME_PLAYER TEXT," +
                "$COLUMN_NAME_STONE_COUNT INTEGER)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
}