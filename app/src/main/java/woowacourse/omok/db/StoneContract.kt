package woowacourse.omok.db

import android.provider.BaseColumns

object StoneContract {
    object StoneEntry : BaseColumns {
        const val TABLE_NAME = "stones"
        const val COLUMN_NAME_X = "x"
        const val COLUMN_NAME_Y = "y"
        const val COLUMN_NAME_STONECOLOR = "stonecolor"
    }
}
