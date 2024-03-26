package omok.model

sealed class Stone(
    val color: Color,
    val position: Position,
) {
    data class Black(val stonePosition: Position) : Stone(Color.BLACK, stonePosition)

    data class White(val stonePosition: Position) : Stone(Color.WHITE, stonePosition)
}
