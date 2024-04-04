package woowacourse.omok.util

import woowacourse.omok.model.omok.Board
import woowacourse.omok.model.omok.OmokStone
import woowacourse.omok.model.omok.Position
import woowacourse.omok.model.omok.StoneColor

fun Board.toArkOmokBoard(): List<List<Int>> {
    val arkBoard = MutableList(15) { MutableList(15) { 0 } }
    stones.keys.forEach {
        arkBoard[it.y - 1][it.x - 1] = get(it).toInt()
    }
    return arkBoard
}

fun Position.toArkOmokPoint(): Pair<Int, Int> {
    return Pair(x - 1, y - 1)
}

private fun OmokStone?.toInt(): Int {
    return when (this?.color) {
        StoneColor.BLACK -> 1
        StoneColor.WHITE -> 2
        null -> 0
    }
}
