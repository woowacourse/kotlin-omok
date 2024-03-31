package woowacourse.omok.ui

import android.content.Context
import woowacourse.omok.R
import woowacourse.omok.model.board.Stone

fun Stone.stoneImage(): Int {
    return when (this) {
        Stone.BLACK -> R.drawable.black_stone
        Stone.WHITE -> R.drawable.white_stone
        Stone.NONE -> 0
    }
}

fun Stone.message(context: Context): String {
    return when (this) {
        Stone.BLACK -> context.resources.getString(R.string.black_stone)
        Stone.WHITE -> context.resources.getString(R.string.white_stone)
        Stone.NONE -> ""
    }
}