package woowacourse.omok.model.rule

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor

class OmokReferee(
    private val omokRule: OmokRule,
) {
    fun isOmok(board: Board): Boolean {
        val lastStone: Stone = board.lastStone ?: return false
        return omokRule.isOmok(board.stonesMap, lastStone)
    }

    fun lastStoneFoul(board: Board): RenjuFoul {
        val lastStone: Stone = board.lastStone ?: return RenjuFoul.SAFE
        if (lastStone.stoneColor == StoneColor.BLACK) {
            return omokRule.checkLastBlackStoneFoul(board.stonesMap, lastStone)
        }
        return RenjuFoul.SAFE
    }
}
