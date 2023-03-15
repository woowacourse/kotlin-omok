package omok.domain.judgment

import omok.domain.board.Position
import omok.domain.player.Stone

class WinningReferee : Referee() {
    fun hasFiveOrMoreStoneInRow(board: Map<Position, Stone?>, position: Position): Boolean {
        val verticalContinuityCount =
            countNorthContinuity(board, position, 0) + countSouthContinuity(board, position, 0) + 1
        val horizontalContinuityCount =
            countEastContinuity(board, position, 0) + countWestContinuity(board, position, 0) + 1
        val upwardDiagonalContinuityCount =
            countSouthWestContinuity(board, position, 0) + countNorthEastContinuity(board, position, 0) + 1
        val downwardDiagonalContinuityCount =
            countNorthWestContinuity(board, position, 0) + countSouthEastContinuity(board, position, 0) + 1

        return listOf(
            verticalContinuityCount,
            horizontalContinuityCount,
            upwardDiagonalContinuityCount,
            downwardDiagonalContinuityCount
        ).any { it >= 5 }
    }
}
