package omok.model.state

import omok.model.Color
import omok.model.GameResult
import omok.model.Position

class White(whiteStatus: Array<Array<Color?>>) : TurnState(whiteStatus) {
    override fun getWinningResult(
        position: Position,
        markSinglePlace: (horizontalCoordinate: Int, verticalCoordinate: Int, color: Color) -> Unit,
        addSingleStone: (Color, Position) -> Unit,
    ): GameResult? {
        if (isCurrentStoneWinner(position, Color.WHITE, markSinglePlace, addSingleStone)) {
            return GameResult.WINNER_WHITE
        }
        return null
    }

    override fun addStone(
        color: Color,
        position: Position,
        markSinglePlace: (horizontalCoordinate: Int, verticalCoordinate: Int, color: Color) -> Unit,
        addSingleStone: (Color, Position) -> Unit,
    ) {
        val horizontalCoordinate = COMPUTATION_BOARD_SIZE - position.horizontalCoordinate.index
        val verticalCoordinate = position.verticalCoordinate.index
        markSinglePlace(horizontalCoordinate, verticalCoordinate, Color.WHITE)
        addSingleStone(Color.WHITE, position)
    }
}
