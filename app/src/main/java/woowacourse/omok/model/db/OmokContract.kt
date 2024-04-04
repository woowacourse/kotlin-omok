package woowacourse.omok.model.db

import android.provider.BaseColumns

object OmokContract {
    object OmokEntry : BaseColumns {
        const val TABLE_NAME = "omok"
        const val POINT_X = "x"
        const val POINT_Y = "y"
        const val COLOR = "color"
    }
}
