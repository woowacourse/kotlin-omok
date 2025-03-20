package rule.wrapper.direction

import rule.other.Iterator

internal class DirectionIterator(items: List<Direction>) : Iterator<Direction> {
    private val items: MutableList<Direction> = items.toMutableList()

    override fun hasNext(): Boolean = items.isNotEmpty()

    override fun next(): Direction {
        if (hasNext()) return items.removeFirst()
        throw IllegalStateException("The next direction does not exist.")
    }
}
