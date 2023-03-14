package omok.domain

class XLine(val value: Map<XCoordinate, StoneState>) {
    val keys = value.keys
    val values = value.values

    fun placeStone(point: OmokPoint, stoneState: StoneState): XLine {
        val newValue = value.toMutableMap()
        when (newValue[point.x]) {
            StoneState.EMPTY -> newValue[point.x] = stoneState
            else -> throw IllegalArgumentException()
        }
        return XLine(newValue)
    }

    operator fun get(xCoordinate: XCoordinate): StoneState = value[xCoordinate] ?: throw IllegalArgumentException()

    constructor () : this(XCoordinate.all().associateWith { StoneState.EMPTY })
}
