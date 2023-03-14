package omok.domain

class YLine(val value: Map<YCoordinate, XLine>) {
    val keys = value.keys
    val values = value.values

    fun placeStone(point: OmokPoint, stoneState: StoneState): YLine {
        val newValue = value.toMutableMap()
        newValue[point.y] = newValue[point.y]?.placeStone(point, stoneState) ?: throw IllegalArgumentException()
        return YLine(newValue)
    }

    operator fun get(yCoordinate: YCoordinate): XLine = value[yCoordinate] ?: throw IllegalArgumentException()

    constructor () : this(YCoordinate.all().associateWith { XLine() })
}
