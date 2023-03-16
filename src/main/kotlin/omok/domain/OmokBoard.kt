package omok.domain

import omok.domain.state.BlackStoneState
import omok.domain.state.StoneState

class OmokBoard(
    val value: Map<YCoordinate, OmokLine>,
    val stoneState: StoneState = BlackStoneState,
) {

    val keys = value.keys
    val values = value.values

    fun placeStone(point: OmokPoint, stoneState: StoneState = this.stoneState): OmokBoard {
        val newValue = value.toMutableMap()
        newValue[point.y] = newValue[point.y]?.placeStone(point, stoneState) ?: throw IllegalArgumentException()
        return OmokBoard(newValue, stoneState.next())
    }

    operator fun get(yCoordinate: YCoordinate): OmokLine = value[yCoordinate] ?: throw IllegalArgumentException()

    constructor () : this(YCoordinate.all().associateWith { OmokLine() })
}
