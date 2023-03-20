package domain.library.cldhfleks2

import domain.CoordinateState
import domain.Position
import domain.domain.GameRule

class Cldhfleks2Rule : GameRule {
    override fun isForbiddenThree(position: Position, board: List<List<CoordinateState>>): Boolean =
        ForbiddenThree.isForbiddenThree(board, position)

    override fun isForbiddenFour(position: Position, board: List<List<CoordinateState>>): Boolean =
        ForbiddenFour.isForbiddenFour(board, position)

    override fun isExceedFive(position: Position, coordinateState: CoordinateState, board: List<List<CoordinateState>>): Boolean =
        ExceedFive.isExceedFive(board, position, coordinateState)

    override fun isExactlyFive(position: Position, coordinateState: CoordinateState, board: List<List<CoordinateState>>): Boolean =
        ExactlyFive.isExactlyFive(board, position, coordinateState)
}
