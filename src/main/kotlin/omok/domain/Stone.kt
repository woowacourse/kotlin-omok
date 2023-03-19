package omok.domain

interface Stone {
    fun findPosition(value: Position): Boolean

    fun getLocation(): Position
}

// class Stone(val position: Position) {
//     fun findPosition(value: Position) = (position == value)
// }
