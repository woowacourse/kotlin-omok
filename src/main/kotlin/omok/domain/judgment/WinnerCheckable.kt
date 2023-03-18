package omok.domain.judgment

import omok.domain.board.Position
import omok.domain.player.Stone

interface WinnerCheckable {
    fun checkWinner(board: Map<Position, Stone?>, position: Position): Boolean
}