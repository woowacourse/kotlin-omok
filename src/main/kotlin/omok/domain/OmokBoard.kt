package omok.domain

import omok.domain.state.StoneState

class OmokBoard(private val value: Map<YCoordinate, OmokLine>) {

    val keys = value.keys
    val values = value.values

    constructor () : this(YCoordinate.all().associateWith { OmokLine() })

    fun placeStone(point: OmokPoint, stoneState: StoneState): OmokBoard {
        val newValue = value.toMutableMap()
        newValue[point.y] = newValue[point.y]?.placeStone(point, stoneState) ?: throw IllegalArgumentException()
        return OmokBoard(newValue)
    }

    operator fun get(yCoordinate: YCoordinate): OmokLine = value[yCoordinate] ?: throw IllegalArgumentException()
}
