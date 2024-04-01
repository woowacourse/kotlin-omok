package woowacourse.omok.model.state

import woowacourse.omok.model.Color
import woowacourse.omok.model.GameResult
import woowacourse.omok.model.Position

class White(whiteStatus: Array<Array<Color?>>) : TurnState(whiteStatus) {
    override fun getWinningResult(
        position: Position,
        markSinglePlace: (horizontalCoordinate: Int, verticalCoordinate: Int, color: Color) -> Unit,
    ): GameResult? {
        if (isCurrentStoneWinner(position, Color.WHITE, markSinglePlace)) {
            return GameResult.WINNER_WHITE
        }
        return null
    }

    override fun addStone(
        color: Color,
        position: Position,
        markSinglePlace: (horizontalCoordinate: Int, verticalCoordinate: Int, color: Color) -> Unit,
    ) {
        val horizontalCoordinate = COMPUTATION_BOARD_SIZE - position.horizontalCoordinate
        val verticalCoordinate = position.verticalCoordinate
        markSinglePlace(horizontalCoordinate, verticalCoordinate, Color.WHITE)
    }
}
