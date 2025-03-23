package omok.model

import omok.model.game.FoulCondition
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.Stones

class Board(
    private val blackStones: Stones,
    private val whiteStones: Stones
) {
    val stones: Set<Stone> get() = blackStones.stones + whiteStones.stones

    operator fun contains(
        newStone: Stone,
    ): Boolean {
        return newStone in stones
    }

    fun place(newStone: Stone) {
        when (blackStones.checkAnyFoulCondition(whiteStones, newStone)) {
            FoulCondition.DOUBLE_THREE -> throw IllegalArgumentException(ERROR_MESSAGE_DOUBLE_THREE_VIOLATION)
            FoulCondition.DOUBLE_FOUR -> throw IllegalArgumentException(ERROR_MESSAGE_DOUBLE_FOUR_VIOLATION)
            FoulCondition.OVERLINE -> throw IllegalArgumentException(ERROR_MESSAGE_OVERLINE_VIOLATION)
            FoulCondition.NONE -> placeByColor(newStone)
        }
    }

    fun hasOmok(newStone: Stone): Boolean {
        return when(newStone.color) {
            StoneColor.WHITE -> whiteStones.checkWin(whiteStones, newStone)
            StoneColor.BLACK -> blackStones.checkWin(blackStones, newStone)
        }
    }

    private fun placeByColor(newStone: Stone) {
        when(newStone.color) {
            StoneColor.WHITE -> whiteStones.add(newStone)
            StoneColor.BLACK -> blackStones.add(newStone)
        }
    }

    companion object {
        private const val ERROR_MESSAGE_IS_ALREADY_OCCUPIED = "이미 돌이 있는 자리입니다."
        private const val ERROR_MESSAGE_DOUBLE_THREE_VIOLATION = "삼삼 금수입니다."
        private const val ERROR_MESSAGE_DOUBLE_FOUR_VIOLATION = "사사 금수입니다."
        private const val ERROR_MESSAGE_OVERLINE_VIOLATION = "장목 금수입니다."
    }
}