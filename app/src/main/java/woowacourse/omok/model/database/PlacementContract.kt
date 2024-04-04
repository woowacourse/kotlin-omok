package woowacourse.omok.model.database

import android.provider.BaseColumns

object PlacementContract : BaseColumns {
    const val TABLE_NAME = "placement"
    const val COLUMN_ROOM_ID = "room_id"
    const val COLUMN_COLOR = "color"
    const val COLUMN_PLACEMENT_HORIZONTAL_COORDINATE = "placement_horizontal_coordinate"
    const val COLUMN_PLACEMENT_VERTICAL_COORDINATE = "placement_vertical_coordinate"
}
