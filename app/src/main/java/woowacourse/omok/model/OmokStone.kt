package omok.model

enum class OmokStoneType {
    BLACK,
    WHITE,
    EMPTY,
}

sealed class OmokStone(open val boardPosition: BoardPosition, private val type: OmokStoneType) {
    fun isSameType(opponent: OmokStone): Boolean = this.type == opponent.type

    fun getRow() = boardPosition.getRow()

    fun getColumn() = boardPosition.getColumn()

    fun getOmokStoneType() = type
}

data class Black(override val boardPosition: BoardPosition) : OmokStone(
    boardPosition,
    OmokStoneType.BLACK,
) {
}

data class White(override val boardPosition: BoardPosition) : OmokStone(
    boardPosition,
    OmokStoneType.WHITE,
) {
}

class Empty : OmokStone(BoardPosition(BoardCoordinate(-1), BoardCoordinate(-1)), OmokStoneType.EMPTY) {
}
