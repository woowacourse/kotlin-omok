package omok.model.state

import omok.model.Color
import omok.model.GameResult
import omok.model.Position

class White(whiteStatus: Array<Array<Color?>>) : TurnState(whiteStatus) {
    override fun getWinningResult(
        position: Position,
        placeStone: (Color, Position) -> Unit,
    ): GameResult? {
        if (isCurrentTurnWin(position, Color.WHITE, placeStone)) {
            return GameResult.WINNER_WHITE
        }
        return null
    }

    override fun addStone(
        color: Color,
        position: Position,
        placeStone: (Color, Position) -> Unit,
    ) {
        placeStone(Color.WHITE, position)
    }
}
