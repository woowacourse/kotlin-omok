package woowacourse.omok.model.database

import android.provider.BaseColumns

object GameRoomContract : BaseColumns {
    const val TABLE_NAME = "room"
    const val COLUMN_TITLE = "title"
    const val COLUMN_STATUS = "status"
}
