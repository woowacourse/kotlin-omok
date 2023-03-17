package omok.domain

import omok.domain.state.EmptyStoneState
import omok.domain.state.StoneState

class OmokBoard(private val value: Map<OmokPoint, StoneState>) {

    val keys = value.keys
    val values = value.values

    constructor () : this(
        (X_MIN_RANGE..X_MAX_RANGE)
            .flatMap { x -> (Y_MIN_RANGE..Y_MAX_RANGE).map { y -> OmokPoint(x, y) } }
            .associateWith { EmptyStoneState },
    )

    fun placeStone(point: OmokPoint, stoneState: StoneState): OmokBoard {
        val newValue = value.toMutableMap()
        newValue[point] = when (newValue[point]) {
            EmptyStoneState -> stoneState
            else -> throw IllegalArgumentException(ERROR_ALREADY_PLACED.format(point.x.toChar(), point.y.value))
        }
        return OmokBoard(newValue)
    }

    operator fun get(point: OmokPoint): StoneState = value[point]
        ?: throw IllegalArgumentException(ERROR_INVALID_POINT.format(point.x.value, point.y.value))

    companion object {
        const val X_MIN_RANGE = 1
        const val X_MAX_RANGE = 15
        const val Y_MIN_RANGE = 1
        const val Y_MAX_RANGE = 15
        private const val ERROR_ALREADY_PLACED = "좌표 %s%s는 이미 다른 돌이 있습니다."
        private const val ERROR_INVALID_POINT = "좌표 %s%s는 오목판에 없습니다."
    }
}
