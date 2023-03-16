package omok.domain

import omok.domain.state.EmptyStoneState
import omok.domain.state.StoneState

class OmokLine(private val value: Map<XCoordinate, StoneState>) {
    val keys = value.keys
    val values = value.values

    constructor () : this(XCoordinate.all().associateWith { EmptyStoneState })

    fun placeStone(point: OmokPoint, stoneState: StoneState): OmokLine {
        val newValue = value.toMutableMap()
        when (newValue[point.x]) {
            EmptyStoneState -> newValue[point.x] = stoneState
            else -> throw IllegalArgumentException()
        }
        return OmokLine(newValue)
    }

    operator fun get(xCoordinate: XCoordinate): StoneState = value[xCoordinate] ?: throw IllegalArgumentException()
}
