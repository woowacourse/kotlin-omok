package omok.model

data class Direction(val x: Int, val y: Int) {
    fun invert(): Direction {
        return Direction(-x, -y)
    }
}
