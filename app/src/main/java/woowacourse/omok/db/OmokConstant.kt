package woowacourse.omok.db

import android.provider.BaseColumns

object OmokConstant : BaseColumns {
    const val TABLE_NAME = "stone"
    const val TABLE_COLUMN_X = "x_coordinate"
    const val TABLE_COLUMN_Y = "y_coordinate"
    const val TABLE_COLUMN_COLOR = "color"

    const val STONE_COLOR_BLACK = "BLACK"
    const val STONE_COLOR_WHITE = "WHITE"
}
