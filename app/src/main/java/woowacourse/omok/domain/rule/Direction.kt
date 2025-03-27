package woowacourse.omok.domain.rule

enum class Direction(val y: Int, val x: Int) {
    TOP(1, 0) {
        override fun reverse(): Direction = BOTTOM
    },
    BOTTOM(-1, 0) {
        override fun reverse(): Direction = TOP
    },
    LEFT(0, -1) {
        override fun reverse(): Direction = RIGHT
    },
    RIGHT(0, 1) {
        override fun reverse(): Direction = LEFT
    },
    TOP_RIGHT(1, 1) {
        override fun reverse(): Direction = BOTTOM_LEFT
    },
    TOP_LEFT(1, -1) {
        override fun reverse(): Direction = BOTTOM_RIGHT
    },
    BOTTOM_LEFT(-1, -1) {
        override fun reverse(): Direction = TOP_RIGHT
    },
    BOTTOM_RIGHT(-1, 1) {
        override fun reverse(): Direction = TOP_LEFT
    }, ;

    abstract fun reverse(): Direction

    companion object {
        fun getDirectionPair(): List<Pair<Direction, Direction>> {
            return listOf(
                TOP to BOTTOM,
                LEFT to RIGHT,
                TOP_RIGHT to BOTTOM_LEFT,
                TOP_LEFT to BOTTOM_RIGHT,
            )
        }
    }
}
