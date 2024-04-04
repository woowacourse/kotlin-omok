package woowacourse.omok.domain.model.rule.library

import woowacourse.omok.domain.model.rule.ContinualStonesStandard
import woowacourse.omok.domain.model.rule.ContinualStonesStandard.Companion.MIN_CONTINUAL_STONES_COUNT
import woowacourse.omok.domain.model.rule.winning.ContinualStonesWinningCondition

class OverlineRule(
    currentStone: Int,
    opponentStone: Int,
    private val continualStonesWinningCondition: ContinualStonesWinningCondition,
) : OmokRule(currentStone, opponentStone) {
    override fun abide(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
    ): Boolean {
        return directions.all { direction -> !checkOverline(board, position, direction) }
    }

    private fun checkOverline(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Boolean {
        val oppositeDirection = direction.let { (dx, dy) -> Pair(-dx, -dy) }
        val (stone1, blink1) = search(board, position, oppositeDirection)
        val (stone2, blink2) = search(board, position, direction)

        return blink1 + blink2 == 0 &&
                continualStonesWinningCondition.continualStonesCondition.overline(
                    (stone1 + stone2 + 1),
                    continualStonesWinningCondition.continualStonesStandard,
                )
    }

    fun isWin(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
    ): Boolean {
        return directions.any { direction -> checkWin(board, position, direction) }
    }

    private fun checkWin(
        board: List<List<Int>>,
        position: Pair<Int, Int>,
        direction: Pair<Int, Int>,
    ): Boolean {
        val oppositeDirection = direction.let { (dx, dy) -> Pair(-dx, -dy) }
        val (stone1, blink1) = search(board, position, oppositeDirection)
        val (stone2, blink2) = search(board, position, direction)

        val stone1Only = searchOnlyStone(board, position, oppositeDirection)
        val stone2Only = searchOnlyStone(board, position, direction)

        return (blink1 + blink2 == 0 && checkWin(stone1, stone2)) || checkWin(stone1Only) || checkWin(stone2Only)
    }

    fun canHaveDoubleRule(): Boolean =
        continualStonesWinningCondition.continualStonesStandard > ContinualStonesStandard(MIN_CONTINUAL_STONES_COUNT)

    private fun checkWin(
        stone1: Int,
        stone2: Int = 0,
    ) = continualStonesWinningCondition.continualStonesCondition.win(
        (stone1 + stone2 + 1),
        continualStonesWinningCondition.continualStonesStandard,
    )

    companion object {
        fun forBlack(continualStonesWinningCondition: ContinualStonesWinningCondition): OverlineRule =
            OverlineRule(BLACK_STONE, WHITE_STONE, continualStonesWinningCondition)

        fun forWhite(continualStonesWinningCondition: ContinualStonesWinningCondition): OverlineRule =
            OverlineRule(WHITE_STONE, BLACK_STONE, continualStonesWinningCondition)
    }
}
