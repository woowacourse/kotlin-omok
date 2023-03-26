package omok.model.external.rule.wrapper.direction

import omok.model.external.rule.Col
import omok.model.external.rule.Direction
import omok.model.external.rule.Row
import omok.model.external.rule.other.Iterator

class DirectionsIterator(items: List<Direction<Row, Col>>) : Iterator<Direction<Row, Col>> {
    private val _items: MutableList<Direction<Row, Col>> = items.deepCopy()

    override fun hasNext(): Boolean = _items.isNotEmpty()

    override fun next(): Direction<Row, Col> {
        if (hasNext()) return _items.removeFirst()
        throw IllegalStateException("The next direction does not exist.")
    }

    private fun List<Direction<Row, Col>>.deepCopy(): MutableList<Direction<Row, Col>> =
        map { it.copy() }.toMutableList()
}
