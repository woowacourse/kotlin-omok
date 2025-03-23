package omok.model.rule

import omok.model.board.Board
import omok.model.stone.Stone
import omok.model.stone.StoneColor

class OmokReferee(
    private val renjuRule: RenjuRule,
) {
    fun isLastStoneOmok(board: Board): Boolean = renjuRule.isLastStoneOmok(board)

    fun lastStoneFoulCheck(board: Board) {
        val lastStone: Stone = board.lastStone ?: return
        if (lastStone.stoneColor == StoneColor.BLACK) {
            val foulType = renjuRule.checkLastBlackStoneFoul(board)
            when (foulType) {
                RenjuFoul.THREE_BY_THREE_FOUL -> throw Exception(ERROR_THREE_BY_THREE_FOUL)
                RenjuFoul.FOUR_BY_FOUR_FOUL -> throw Exception(ERROR_FOUR_BY_FOUR_FOUL)
                RenjuFoul.OVER_FIVE_FOUL -> throw Exception(ERROR_OVER_FIVE_FOUL)
                RenjuFoul.SAFE -> {}
            }
        }
    }

    companion object {
        private const val ERROR_THREE_BY_THREE_FOUL = "3-3 반칙이 발생했습니다"
        private const val ERROR_FOUR_BY_FOUR_FOUL = "4-4 반칙이 발생했습니다"
        private const val ERROR_OVER_FIVE_FOUL = "장목 반칙이 발생했습니다"
    }
}
