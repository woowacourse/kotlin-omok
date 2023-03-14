package omok.domain

class YLine(val value: Map<YCoordinate, XLine>) {
    val keys = value.keys
    val values = value.values

    fun placeStone(xCoordinate: XCoordinate, yCoordinate: YCoordinate, stoneState: StoneState): YLine {
        val newValue = value.toMutableMap()
        newValue[yCoordinate] = newValue[yCoordinate]?.placeStone(xCoordinate, stoneState) ?: throw IllegalArgumentException()
        return YLine(newValue)
    }

    operator fun get(yCoordinate: YCoordinate): XLine = value[yCoordinate] ?: throw IllegalArgumentException()

    constructor () : this(YCoordinate.all().associateWith { XLine() })
}
