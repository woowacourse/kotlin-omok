package omok.model

enum class OmokStoneType {
    BLACK,
    WHITE,
    EMPTY,
}

sealed class OmokStone(open val boardPosition: BoardPosition, private val type: OmokStoneType) {
    abstract val isBlack: Boolean
    abstract val isWhite: Boolean
    abstract val isEmpty: Boolean

    fun isSameType(opponent: OmokStone): Boolean = this.type == opponent.type

    fun getRow() = boardPosition.getRow()

    fun getColumn() = boardPosition.getColumn()
}

data class Black(override val boardPosition: BoardPosition) : OmokStone(
    boardPosition,
    OmokStoneType.BLACK,
) {
    override val isBlack = true
    override val isWhite = false
    override val isEmpty = false
}

data class White(override val boardPosition: BoardPosition) : OmokStone(
    boardPosition,
    OmokStoneType.WHITE,
) {
    override val isBlack = false
    override val isWhite = true
    override val isEmpty = false
}

class Empty : OmokStone(BoardPosition(BoardCoordinate(-1), BoardCoordinate(-1)), OmokStoneType.EMPTY) {
    override val isBlack = false
    override val isWhite = false
    override val isEmpty = true
}
