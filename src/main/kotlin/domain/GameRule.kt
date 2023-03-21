package domain.domain

import domain.CoordinateState
import domain.Position

interface GameRule {

    fun isForbiddenThree(position: Position, board: List<List<CoordinateState>>): Boolean

    fun isForbiddenFour(position: Position, board: List<List<CoordinateState>>): Boolean

    fun isExceedFive(
        position: Position,
        coordinateState: CoordinateState,
        board: List<List<CoordinateState>>,
    ): Boolean = throw IllegalStateException(NOT_OVERRIDE_ERROR)

    fun isExactlyFive(
        position: Position,
        coordinateState: CoordinateState,
        board: List<List<CoordinateState>>,
    ): Boolean = throw IllegalStateException(NOT_OVERRIDE_ERROR)

    companion object {
        private const val NOT_OVERRIDE_ERROR = "올바르지 않은 접근입니다(override 되지않음)GameRule Adapter"
    }
}
