class Stone(
    val position: Position,
    val color: StoneColor,
) {
    fun isSamePosition(stone: Stone): Boolean = position.isSamePosition(stone.position)

    fun isSameColor(stone: Stone): Boolean = color.isSameColor(stone.color)
}
