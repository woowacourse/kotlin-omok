import rule.wrapper.position.Position

class Stone(
    val position: Position,
    val color: StoneColor,
) {
    fun isSamePosition(stone: Stone): Boolean = position.isSame(stone.position)

    fun isSameColor(stone: Stone): Boolean = color.isSameColor(stone.color)

    companion object {
        fun of(
            position: String,
            color: StoneColor,
        ): Stone {
            val r = position.substring(1)
            val c = position[0]
            // TODO : 행에 숫자가 아닌 값이 들어온다면 예외처리 필요
            return Stone(Position(Row.from(r.toInt()), Col.from(c)), color)
        }
    }
}
