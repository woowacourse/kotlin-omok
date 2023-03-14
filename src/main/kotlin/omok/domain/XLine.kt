package omok.domain

class XLine(val value: Map<XCoordinate, StoneState>) {
    val keys = value.keys
    val values = value.values

    fun placeStone(xCoordinate: XCoordinate, stoneState: StoneState): XLine {
        val newValue = value.toMutableMap()
        when (newValue[xCoordinate]) {
            StoneState.EMPTY -> newValue[xCoordinate] = stoneState
            else -> throw IllegalArgumentException()
        }
        return XLine(newValue)
    }

    operator fun get(xCoordinate: XCoordinate): StoneState = value[xCoordinate] ?: throw IllegalArgumentException()

    constructor () : this(XCoordinate.all().associateWith { StoneState.EMPTY })
}
