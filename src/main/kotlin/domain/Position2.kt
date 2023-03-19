package domain.domain

data class Position2(val x: Int, val y: Int) {
    fun move(distanceX: Int, distanceY: Int): Position2 {
        return Position2(x + distanceX, y + distanceY)
    }
}
