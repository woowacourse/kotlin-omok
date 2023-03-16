package omok.domain

class Stone(val position: Position) {
    fun findPosition(value: Position): Boolean {
        return position == value
    }
}
