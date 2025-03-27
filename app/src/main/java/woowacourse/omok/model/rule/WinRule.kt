package woowacourse.omok.model.rule

import woowacourse.omok.model.board.OmokBoard
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.PositionState

class WinRule(
    currentStone: PositionState,
    position: Position,
    omokBoard: OmokBoard,
) : OmokRule(
        currentStone,
        getOpponentStone(currentStone),
        position = position,
        omokBoard = omokBoard,
    ) {
    override fun validate(): Boolean = directions.map { direction -> checkWhiteWin(direction) }.contains(true)

    private fun checkWhiteWin(direction: Pair<Int, Int>): Boolean {
        val oppositeDirection = direction.let { (dx, dy) -> Pair(-dx, -dy) }
        val (stone1, blink1) = search(oppositeDirection)
        val (stone2, blink2) = search(direction)

        return when {
            blink1 + blink2 == 0 && stone1 + stone2 == 4 -> true
            else -> false
        }
    }

    companion object {
        private fun getOpponentStone(stone: PositionState): PositionState =
            if (stone == PositionState.BLACK_POSITION) PositionState.WHITE_POSITION else PositionState.BLACK_POSITION
    }
}
