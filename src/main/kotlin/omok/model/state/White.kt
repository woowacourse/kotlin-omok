package omok.model.state

import omok.model.Color
import omok.model.Column
import omok.model.GameResult
import omok.model.Position

class White(whiteStatus: Array<Array<Color?>>) : TurnState(whiteStatus) {
    override fun getWinningResult(
        position: Position,
        markSinglePlace: (row: Int, col: Int, color: Color) -> Unit,
        addSingleStone: (Color, Position) -> Unit,
    ): GameResult? {
        if (isCurrentStoneWinner(position, Color.WHITE, markSinglePlace, addSingleStone)) {
            return GameResult.WINNER_WHITE
        }
        return null
    }

    override fun addStone(
        row: Int,
        col: Char,
        color: Color,
        position: Position,
        markSinglePlace: (row: Int, col: Int, color: Color) -> Unit,
        addSingleStone: (Color, Position) -> Unit,
    ) {
        val column = Column.valueOf(col)?.value ?: return
        markSinglePlace(row, column, Color.WHITE)
        addSingleStone(Color.WHITE, position)
    }
}
