package woowacourse.omok.model.database

import android.provider.BaseColumns

object PlacementContract : BaseColumns {
    const val TABLE_NAME = "placement"
    const val COLUMN_COLOR = "color"
    const val COLUMN_VERTICAL_COORDINATE = "vertical_coordinate"
    const val COLUMN_HORIZONTAL_COORDINATE = "horizontal_coordinate"
}
