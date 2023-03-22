package omok.domain

import omok.domain.state.EmptyStoneState
import omok.domain.state.StoneState

class OmokBoard(value: Map<OmokPoint, StoneState> = EMPTY_BOARD) {
    val value: Map<OmokPoint, StoneState> = value.toMap()

    fun placeStone(point: OmokPoint, stoneState: StoneState): OmokBoard {
        val newValue = value.toMutableMap()
        newValue[point] = when (newValue[point]) {
            EmptyStoneState -> stoneState
            else -> throw IllegalArgumentException(ERROR_ALREADY_PLACED.format(point.x, point.y))
        }
        return OmokBoard(newValue)
    }
    operator fun get(point: OmokPoint): StoneState = value[point]
        ?: throw IllegalArgumentException(ERROR_INVALID_POINT)

    companion object {
        private const val BOARD_X_SIZE = 15
        private const val BOARD_Y_SIZE = 15
        private const val ERROR_ALREADY_PLACED = "(%s, %s)는 이미 다른 돌이 있습니다."
        private const val ERROR_INVALID_POINT = "해당 좌표는 오목판에 없습니다."
        private val EMPTY_BOARD = OmokPoint.createLinesPoint(BOARD_X_SIZE, BOARD_Y_SIZE).associateWith { EmptyStoneState }
    }
}
