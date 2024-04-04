package woowacourse.omok.util

import woowacourse.omok.model.omok.OmokStone
import woowacourse.omok.model.omok.StoneColor

fun mapStoneColorToString(color: StoneColor): String {
    return when (color) {
        StoneColor.BLACK -> "흑"
        StoneColor.WHITE -> "백"
    }
}

fun getNextTurnColor(stone: OmokStone) =
    when (stone.color) {
        StoneColor.BLACK -> "백"
        StoneColor.WHITE -> "흑"
    }
