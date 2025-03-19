package omok.domain

enum class Direction(val y: Int, val x: Int) {
    TOP(1, 0),
    BOTTOM(-1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    TOP_RIGHT(1, 1),
    TOP_LEFT(1, -1),
    BOTTOM_LEFT(-1, -1),
    BOTTOM_RIGHT(-1, 1);

    companion object {
        fun getDirectionPair():List<Pair<Direction, Direction>> {
            return listOf(
                TOP to BOTTOM,
                LEFT to RIGHT,
                TOP_RIGHT to BOTTOM_LEFT,
                TOP_LEFT to BOTTOM_RIGHT
            )
        }
    }
}
