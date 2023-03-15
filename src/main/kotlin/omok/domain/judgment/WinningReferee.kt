package omok.domain.judgment

import omok.domain.board.Position
import omok.domain.player.Stone

class WinningReferee : Referee() {
    fun hasFiveOrMoreStoneInRow(board: Map<Position, Stone?>, position: Position): Boolean {
        return listOf(
            countVerticalContinuity(board, position, 0),
            countUpwardDiagonalContinuity(board, position, 0),
            countHorizontalContinuity(board, position, 0),
            countDownwardDiagonalContinuity(board, position, 0)
        ).any { it >= 5 }
    }
}
