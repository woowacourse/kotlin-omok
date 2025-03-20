package rule.wrapper.direction

import rule.other.Iterator

internal class DirectionIterator(items: List<Direction>) : Iterator<Direction> {
    private val _items: MutableList<Direction> = items.toMutableList()

    override fun hasNext(): Boolean = _items.isNotEmpty()

    override fun next(): Direction {
        if (hasNext()) return _items.removeFirst()
        throw IllegalStateException("The next direction does not exist.")
    }
}
