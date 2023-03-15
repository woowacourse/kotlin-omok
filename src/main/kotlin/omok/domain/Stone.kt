package omok.domain

abstract class Stone(open val position: Position) {
    fun findPosition(value: Position): Boolean {
        return position == value
    }
}
