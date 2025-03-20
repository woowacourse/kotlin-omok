import rule.wrapper.position.Position

class Stone(
    val position: Position,
    val color: StoneColor,
) {
    fun isSamePosition(stone: Stone): Boolean = position.isSame(stone.position)

    fun isSameColor(stone: Stone): Boolean = color.isSameColor(stone.color)

    companion object {
        fun of(
            position: Position,
            color: StoneColor,
        ): Stone = Stone(position, color)
    }
}
