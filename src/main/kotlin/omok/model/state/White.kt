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
        if (isCurrentTurnWin(position, Color.WHITE, markSinglePlace, addSingleStone)) {
            return GameResult.WINNER_WHITE
        }
        return null
    }

    override fun addStone(
        color: Color,
        position: Position,
        markSinglePlace: (row: Int, col: Int, color: Color) -> Unit,
        addSingleStone: (Color, Position) -> Unit,
    ) {
        val row = ARRAY_SIZE - position.row.value
        val column = Column.titleOf(position.col.title)?.value ?: return
        markSinglePlace(Color.WHITE, position)
        addSingleStone(Color.WHITE, position)
    }
}
