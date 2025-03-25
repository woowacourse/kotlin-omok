package omok.model.rule

import omok.model.board.Board
import omok.model.stone.Stone
import omok.model.stone.StoneColor

class OmokReferee(
    private val renjuRule: RenjuRule,
) {
    fun isLastStoneOmok(board: Board): Boolean = renjuRule.isLastStoneOmok(board)

    fun lastStoneFoul(board: Board): RenjuFoul {
        val lastStone: Stone = board.lastStone ?: return RenjuFoul.SAFE
        if (lastStone.stoneColor == StoneColor.BLACK) {
            return renjuRule.checkLastBlackStoneFoul(board)
        }
        return RenjuFoul.SAFE
    }
}
