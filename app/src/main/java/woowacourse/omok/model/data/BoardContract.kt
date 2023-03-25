package woowacourse.omok.model.data

import android.provider.BaseColumns

object BoardContract {
    const val VERSION_ONE = 1
    const val NAME = "omokBoard.db"
    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${FeedEntry.TABLE_NAME} (" +
            "${FeedEntry.TABLE_COLUMN_LOCATION} int,"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedEntry.TABLE_NAME}"

    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "board"
        const val TABLE_COLUMN_LOCATION = "location"
    }
}
