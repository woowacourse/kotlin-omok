package omok.model.rule.winning

import omok.model.Board
import omok.model.Position

interface WinningCondition {
    fun isWin(board: Board, position: Position): Boolean
}
