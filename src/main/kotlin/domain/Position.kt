package domain

data class Position(val x: Int, val y: Int) {
    fun move(distanceX: Int, distanceY: Int): Position {
        return Position(x + distanceX, y + distanceY)
    }
}
