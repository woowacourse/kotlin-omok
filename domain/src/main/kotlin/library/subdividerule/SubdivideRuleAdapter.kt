package domain.library.subdividerule

import domain.domain.CoordinateState
import domain.domain.CoordinateState.BLACK
import domain.domain.CoordinateState.WHITE
import domain.domain.Position
import domain.domain.BoardState
import domain.domain.GameRule

class SubdivideRuleAdapter : GameRule {

    override fun isBlackWin(
        position: Position,
        board: BoardState,
    ): Boolean = isExactlyFive(position, BLACK, board.value)

    override fun isWhiteWin(
        position: Position,
        board: BoardState,
    ): Boolean = isExactlyFive(position, WHITE, board.value) || isExceedFive(position, WHITE, board.value)

    override fun checkAddablePosition(board: BoardState, turn: CoordinateState, position: Position): Boolean {
        if (!board.isEmpty(position)) return false
        if (turn == BLACK && isBlackForbidden(position, board)) return false
        return true
    }

    private fun isBlackForbidden(position: Position, board: BoardState): Boolean =
        isForbiddenThree(position, board.value) or isForbiddenFour(position, board.value) or isExceedFive(
            position,
            BLACK,
            board.value,
        )

    private fun isForbiddenThree(position: Position, board: List<List<CoordinateState>>): Boolean =
        ForbiddenThree.isForbiddenThree(board, position)

    private fun isForbiddenFour(position: Position, board: List<List<CoordinateState>>): Boolean =
        ForbiddenFour.isForbiddenFour(board, position)

    private fun isExceedFive(
        position: Position,
        coordinateState: CoordinateState,
        board: List<List<CoordinateState>>,
    ): Boolean =
        ExceedFive.isExceedFive(board, position, coordinateState)

    private fun isExactlyFive(
        position: Position,
        coordinateState: CoordinateState,
        board: List<List<CoordinateState>>,
    ): Boolean =
        ExactlyFive.isExactlyFive(board, position, coordinateState)
}
