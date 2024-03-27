package omok.model

enum class OmokStoneType {
    BLACK,
    WHITE,
    EMPTY,
}

sealed class OmokStone(open val rowCoords: CoordsNumber, open val columnCoords: CoordsNumber, private val type: OmokStoneType) {
    fun isSameType(opponent: OmokStone): Boolean = this.type == opponent.type
}

data class Black(override val rowCoords: CoordsNumber, override val columnCoords: CoordsNumber) : OmokStone(
    rowCoords,
    columnCoords,
    OmokStoneType.BLACK,
)

data class White(override val rowCoords: CoordsNumber, override val columnCoords: CoordsNumber) : OmokStone(
    rowCoords,
    columnCoords,
    OmokStoneType.WHITE,
)

data class Empty(
    override val rowCoords: CoordsNumber = CoordsNumber(-100),
    override val columnCoords: CoordsNumber = CoordsNumber(-100),
) : OmokStone(rowCoords, columnCoords, OmokStoneType.EMPTY)
