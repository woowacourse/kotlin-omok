package omok.domain

import omok.configure.Constants.X_MAX_RANGE
import omok.configure.Constants.X_MIN_RANGE
import omok.configure.Constants.Y_MAX_RANGE
import omok.configure.Constants.Y_MIN_RANGE
import omok.domain.state.EmptyStoneState
import omok.domain.state.StoneState

class OmokBoard(value: Map<OmokPoint, StoneState> = EMPTY.toMap()) {
    private val map: Map<OmokPoint, StoneState>

    val keys = value.keys
    val values = value.values
    val ySize = Y_MAX_RANGE - Y_MIN_RANGE + 1
    val xSize = X_MAX_RANGE - X_MIN_RANGE + 1

    init {
        val newMap = EMPTY.toMutableMap()
        newMap.keys.forEach { newMap[it] = value[it] ?: EmptyStoneState }
        this.map = newMap
    }

    fun placeStone(point: OmokPoint, stoneState: StoneState): OmokBoard {
        val newValue = map.toMutableMap()
        newValue[point] = when (newValue[point]) {
            EmptyStoneState -> stoneState
            else -> throw IllegalArgumentException(ERROR_ALREADY_PLACED.format(point.x.toChar(), point.y.value))
        }
        return OmokBoard(newValue)
    }

    operator fun get(point: OmokPoint): StoneState = map[point]
        ?: throw IllegalArgumentException(ERROR_INVALID_POINT.format(point.x.value, point.y.value))

    companion object {
        private const val ERROR_ALREADY_PLACED = "좌표 %s%s는 이미 다른 돌이 있습니다."
        private const val ERROR_INVALID_POINT = "좌표 %s%s는 오목판에 없습니다."

        val EMPTY: Map<OmokPoint, StoneState> = (X_MIN_RANGE..X_MAX_RANGE)
            .flatMap { x -> (Y_MIN_RANGE..Y_MAX_RANGE).map { y -> OmokPoint(x, y) } }
            .associateWith { EmptyStoneState }
    }
}
