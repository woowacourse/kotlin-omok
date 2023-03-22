package woowacourse.omok

import android.provider.BaseColumns

object BoardContract : BaseColumns {
    const val DATABASE_NAME = "Omok.db"
    const val TABLE_NAME = "board"
    const val TABLE_COLUMN_ID = "id"
    const val TABLE_COLUMN_COLUMN = "column"
    const val TABLE_COLUMN_ROW = "row"
    const val TABLE_COLUMN_STONE = "stone"
}
