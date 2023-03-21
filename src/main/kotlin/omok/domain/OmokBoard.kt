package omok.domain

import omok.domain.state.EmptyStoneState
import omok.domain.state.StoneState

class OmokBoard(value: Map<OmokPoint, StoneState>) {
    private val _value = value.toMutableMap()
    val value: Map<OmokPoint, StoneState>
        get() = _value.toMap()

    constructor (xSize: Int = BOARD_X_SIZE, ySize: Int = BOARD_Y_SIZE) : this(OmokPoint.createLinesPoint(xSize, ySize).associateWith { EmptyStoneState })

    fun placeStone(point: OmokPoint, stoneState: StoneState): OmokBoard {
        val newValue = value.toMutableMap()
        newValue[point] = when (newValue[point]) {
            EmptyStoneState -> stoneState
            else -> throw IllegalArgumentException(ERROR_ALREADY_PLACED)
        }
        return OmokBoard(newValue)
    }
    operator fun get(point: OmokPoint): StoneState = value[point]
        ?: throw IllegalArgumentException(ERROR_INVALID_POINT)

    companion object {
        private const val BOARD_X_SIZE = 15
        private const val BOARD_Y_SIZE = 15
        private const val ERROR_ALREADY_PLACED = "해당 좌표는 이미 다른 돌이 있습니다."
        private const val ERROR_INVALID_POINT = "해당 좌표는 오목판에 없습니다."
    }
}
