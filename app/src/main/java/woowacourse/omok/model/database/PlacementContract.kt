package woowacourse.omok.model.database

import android.provider.BaseColumns

object PlacementContract : BaseColumns {
    const val TABLE_NAME = "placement"
    const val COLUMN_ROOM_ID = "room_id"
    const val COLUMN_COLOR = "color"
    const val COLUMN_PLACEMENT_INDEX = "placement_index"
}
