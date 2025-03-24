package model

class Stone(
    val position: Position,
    val color: StoneColor,
) {
    fun isSamePosition(stone: Stone): Boolean = position.isSamePosition(stone.position)

    fun isSameColor(stone: Stone): Boolean = color.isSameColor(stone.color)

    companion object {
        fun of(
            position: String,
            color: StoneColor,
        ): Stone {
            val r = position.substring(1)
            val c = position[0]
            return Stone(Position(Row.from(r.toInt()), Col.from(c)), color)
        }
    }
}
