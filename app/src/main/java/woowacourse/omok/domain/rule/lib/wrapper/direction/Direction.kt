package woowacourse.omok.domain.rule.lib.wrapper.direction

import woowacourse.omok.domain.rule.lib.other.Iterator

enum class Direction(
    val rowStep: Int,
    val colStep: Int,
) {
    UP(1, 0),
    DOWN(-1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    UP_LEFT(1, -1),
    DOWN_RIGHT(-1, 1),
    ;

    companion object {
        fun iterator(): Iterator<Direction> = DirectionIterator(all())

        private fun all(): List<Direction> = entries.toList()
    }
}
