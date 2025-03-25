package omok.model

import omok.model.adapter.BlackRenjuRuleAdapter
import omok.model.adapter.WhiteRenjuRuleAdapter
import omok.model.game.FoulCondition
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.Stones

class Board(
    private val blackStones: Stones = Stones(ruleAdapter = BlackRenjuRuleAdapter(MAX_BOARD_WIDTH, MAX_BOARD_HEIGHT)),
    private val whiteStones: Stones = Stones(ruleAdapter = WhiteRenjuRuleAdapter(MAX_BOARD_WIDTH, MAX_BOARD_HEIGHT)),
) {
    val stones: Set<Stone> get() = blackStones.stones + whiteStones.stones

    fun place(newStone: Stone) {
        require(!stones.isOccupied(newStone)) { ERROR_MESSAGE_IS_ALREADY_OCCUPIED }
        when (blackStones.checkAnyFoulCondition(whiteStones, newStone)) {
            FoulCondition.DOUBLE_THREE -> throw IllegalArgumentException(ERROR_MESSAGE_DOUBLE_THREE_VIOLATION)
            FoulCondition.DOUBLE_FOUR -> throw IllegalArgumentException(ERROR_MESSAGE_DOUBLE_FOUR_VIOLATION)
            FoulCondition.OVERLINE -> throw IllegalArgumentException(ERROR_MESSAGE_OVERLINE_VIOLATION)
            FoulCondition.NONE -> placeByColor(newStone)
        }
    }

    fun hasOmok(newStone: Stone): Boolean =
        when (newStone.color) {
            StoneColor.WHITE -> whiteStones.checkWin(whiteStones, newStone)
            StoneColor.BLACK -> blackStones.checkWin(blackStones, newStone)
        }

    private fun placeByColor(newStone: Stone) {
        when (newStone.color) {
            StoneColor.WHITE -> whiteStones.add(newStone)
            StoneColor.BLACK -> blackStones.add(newStone)
        }
    }

    private fun Set<Stone>.isOccupied(newStone: Stone) = newStone.point in map { it.point }

    companion object {
        const val MIN_BOARD_WIDTH = 1
        const val MIN_BOARD_HEIGHT = 1
        const val MAX_BOARD_WIDTH = 15
        const val MAX_BOARD_HEIGHT = 15

        private const val ERROR_MESSAGE_IS_ALREADY_OCCUPIED = "이미 돌이 있는 자리입니다."
        private const val ERROR_MESSAGE_DOUBLE_THREE_VIOLATION = "삼삼 금수입니다."
        private const val ERROR_MESSAGE_DOUBLE_FOUR_VIOLATION = "사사 금수입니다."
        private const val ERROR_MESSAGE_OVERLINE_VIOLATION = "장목 금수입니다."
    }
}
