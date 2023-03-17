package omok.domain

import omok.domain.state.EmptyStoneState
import omok.domain.state.StoneState

class OmokBoard(private val value: Map<OmokPoint, StoneState>) {

    val keys = value.keys
    val values = value.values

    constructor () : this(OmokPoint.all().associateWith { EmptyStoneState })

    fun placeStone(point: OmokPoint, stoneState: StoneState): OmokBoard {
        val newValue = value.toMutableMap()
        newValue[point] = when (newValue[point]) {
            EmptyStoneState -> stoneState
            else -> throw IllegalArgumentException()
        }
        return OmokBoard(newValue)
    }
    operator fun get(point: OmokPoint): StoneState = value[point] ?: throw IllegalArgumentException()
}
