package domain.library.ark

import domain.CoordinateState
import domain.Position
import domain.domain.GameRule

class ArkRule : GameRule {
    override fun isForbiddenThree(position: Position, board: List<List<CoordinateState>>): Boolean {
        TODO("Not yet implemented")
    }

    override fun isForbiddenFour(position: Position, board: List<List<CoordinateState>>): Boolean {
        TODO("Not yet implemented")
    }

    override fun isExceedFive(
        position: Position,
        coordinateState: CoordinateState,
        board: List<List<CoordinateState>>
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun isExactlyFive(
        position: Position,
        coordinateState: CoordinateState,
        board: List<List<CoordinateState>>
    ): Boolean {
        TODO("Not yet implemented")
    }
}
