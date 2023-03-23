package woowacourse.omok

import android.provider.BaseColumns

object BoardContract : BaseColumns {
    const val DATABASE_NAME = "Omok_db"
    const val TABLE_NAME = "board"
    const val TABLE_COLUMN_POSITION_INDEX = "positionIndex"
    const val TABLE_COLUMN_STONE = "stone"
}
