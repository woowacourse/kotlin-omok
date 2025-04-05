package woowacourse.omok.domain.rule

enum class Direction(
    val x: Int,
    val y: Int,
) {
    UP(0, 1) {
        override fun reverse(): Direction = DOWN
    },
    DOWN(0, -1) {
        override fun reverse(): Direction = UP
    },
    LEFT(-1, 0) {
        override fun reverse(): Direction = RIGHT
    },
    RIGHT(1, 0) {
        override fun reverse(): Direction = LEFT
    },
    LEFT_DOWN(-1, -1) {
        override fun reverse(): Direction = RIGHT_UP
    },
    RIGHT_UP(1, 1) {
        override fun reverse(): Direction = LEFT_DOWN
    },
    LEFT_UP(-1, 1) {
        override fun reverse(): Direction = RIGHT_DOWN
    },
    RIGHT_DOWN(1, -1) {
        override fun reverse(): Direction = LEFT_UP
    }, ;

    abstract fun reverse(): Direction

    companion object {
        private val VERTICAL = UP to DOWN
        private val HORIZONTAL = LEFT to RIGHT
        private val DIAGONAL_UP = LEFT_DOWN to RIGHT_UP
        private val DIAGONAL_DOWN = LEFT_UP to RIGHT_DOWN
        val directionPairs = listOf(VERTICAL, HORIZONTAL, DIAGONAL_UP, DIAGONAL_DOWN)
    }
}
