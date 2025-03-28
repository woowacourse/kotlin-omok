package woowacourse.omok.domain.rule.lib.wrapper.direction

import woowacourse.omok.domain.rule.lib.other.Iterator

class DirectionIterator(
    items: List<Direction>,
) : Iterator<Direction> {
    private val items: MutableList<Direction> = items.toMutableList()

    override fun hasNext(): Boolean = items.isNotEmpty()

    override fun next(): Direction {
        if (hasNext()) return items.removeFirst()
        throw IllegalStateException(ERROR_NO_REMAIN_DIRECTION)
    }

    companion object {
        private const val ERROR_NO_REMAIN_DIRECTION = "방향이 존재하지 않습니다."
    }
}
