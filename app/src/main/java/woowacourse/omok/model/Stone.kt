package woowacourse.omok.model

sealed class Stone(
    val color: Color,
) {
    abstract val position: Position

    data class Black(override val position: Position) : Stone(Color.BLACK)

    data class White(override val position: Position) : Stone(Color.WHITE)
}
