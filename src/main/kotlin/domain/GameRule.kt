package domain.domain

import domain.CoordinateState
import domain.Position

interface GameRule {

    fun isForbiddenThree(position: Position, board: List<List<CoordinateState>>): Boolean

    fun isForbiddenFour(position: Position, board: List<List<CoordinateState>>): Boolean

    fun isExceedFive(position: Position, coordinateState: CoordinateState, board: List<List<CoordinateState>>): Boolean

    fun isExactlyFive(position: Position, coordinateState: CoordinateState, board: List<List<CoordinateState>>): Boolean
}
