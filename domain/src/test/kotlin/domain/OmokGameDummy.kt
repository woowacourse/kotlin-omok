package domain

import domain.domain.CoordinateState
import domain.domain.Position

object OmokGameDummy {
    fun getEmptyCoordinate() = Position(11, 11)
    fun getNotEmptyCoordinate() = Position(5, 4)
    fun getEmptyTestBoard() = List(15) { MutableList(15) { CoordinateState.EMPTY } }.apply {
        this[5][4] = CoordinateState.BLACK
    }

    fun getForbiddenThreeCoordinate() = Position(3, 3)
    fun getForbiddenThreeBoard() = List(15) { MutableList(15) { CoordinateState.EMPTY } }.apply {
        this[3][4] = CoordinateState.BLACK
        this[3][6] = CoordinateState.BLACK
        this[4][4] = CoordinateState.BLACK
        this[6][6] = CoordinateState.BLACK
    }

    fun getForbiddenFourCoordinate() = Position(3, 3)
    fun getForbiddenFourBoard() = List(15) { MutableList(15) { CoordinateState.EMPTY } }.apply {
        this[3][4] = CoordinateState.BLACK
        this[3][6] = CoordinateState.BLACK
        this[3][7] = CoordinateState.BLACK
        this[4][4] = CoordinateState.BLACK
        this[6][6] = CoordinateState.BLACK
        this[7][7] = CoordinateState.BLACK
    }

    fun getExceedFiveCoordinate() = Position(4, 6)
    fun getExceedFiveBoard(coordinateState: CoordinateState) =
        List(15) { MutableList(15) { CoordinateState.EMPTY } }.apply {
            this[4][3] = coordinateState
            this[4][4] = coordinateState
            this[4][5] = coordinateState
            this[4][7] = coordinateState
            this[4][8] = coordinateState
        }

    fun getExactlyFiveCoordinate() = Position(4, 6)
    fun getExactlyFiveBoard(coordinateState: CoordinateState): List<MutableList<CoordinateState>> =
        List(15) { MutableList(15) { CoordinateState.EMPTY } }.apply {
            this[4][3] = coordinateState
            this[4][4] = coordinateState
            this[4][5] = coordinateState
            this[4][7] = coordinateState
        }
}
