package domain.rule

import domain.rule.state.EmptyStoneState
import domain.rule.state.StoneState

class OmokLine(val value: Map<XCoordinate, StoneState>) {
    val keys = value.keys
    val values = value.values

    fun placeStone(point: OmokPoint, stoneState: StoneState): OmokLine {
        val newValue = value.toMutableMap()
        when (newValue[point.x]) {
            EmptyStoneState -> newValue[point.x] = stoneState
            else -> throw IllegalArgumentException()
        }
        return OmokLine(newValue)
    }

    operator fun get(xCoordinate: XCoordinate): StoneState = value[xCoordinate] ?: throw IllegalArgumentException()

    constructor () : this(XCoordinate.all().associateWith { EmptyStoneState })
}
