package woowacourse.omok.model

class Stone(
    val position: Position,
    val color: StoneColor,
) {
    companion object {
        fun ofOrNull(
            position: String,
            color: StoneColor,
        ): Stone? {
            val r = position.substring(1)
            val c = position[0]
            return runCatching {
                Stone(Position(Row.from(r.toInt()), Col.from(c)), color)
            }.getOrNull()
        }
    }
}
