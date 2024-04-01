package woowacourse.omok.model.android

import androidx.annotation.DrawableRes
import woowacourse.omok.R

private const val INITIAL_RESOURCE = 0

enum class BlockStateAndroidModel(@DrawableRes val res: Int) {
    BLACK_STONE(R.drawable.black_stone),

    WHITE_STONE(R.drawable.white_stone),

    EMPTY(INITIAL_RESOURCE),
    ;

    fun format(): String {
        return when (this) {
            BLACK_STONE -> "흑돌"
            WHITE_STONE -> "백돌"
            EMPTY -> "빈칸"
        }
    }
}