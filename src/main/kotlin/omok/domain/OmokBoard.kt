package omok.domain

class OmokBoard(
    val value: Map<YCoordinate, OmokLine>,
    val stoneState: StoneState = StoneState.BLACK,
) {

    val keys = value.keys
    val values = value.values

    fun placeStone(point: OmokPoint): OmokBoard {
        val newValue = value.toMutableMap()
        newValue[point.y] = newValue[point.y]?.placeStone(point, stoneState) ?: throw IllegalArgumentException()
        return OmokBoard(newValue, stoneState.next())
    }

    operator fun get(yCoordinate: YCoordinate): OmokLine = value[yCoordinate] ?: throw IllegalArgumentException()

    constructor () : this(YCoordinate.all().associateWith { OmokLine() })
}
