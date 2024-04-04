package woowacourse.omok.model

enum class Direction(
    val direction1: DirectionVector,
    val direction2: DirectionVector,
) {
    HORIZONTAL(Horizontal.LEFT, Horizontal.RIGHT),
    VERTICAL(Vertical.TOP, Vertical.BOTTOM),
    LEFT_DIAGONAL(LeftDiagonal.LEFT_TOP, LeftDiagonal.RIGHT_BOTTOM),
    RIGHT_DIAGONAL(RightDiagonal.TOP_RIGHT, RightDiagonal.BOTTOM_LEFT),
}

interface DirectionVector {
    val dx: Int
    val dy: Int
}

enum class Horizontal : DirectionVector {
    LEFT {
        override val dx: Int = 0
        override val dy: Int = -1
    },
    RIGHT {
        override val dx: Int = 0
        override val dy: Int = 1
    },
}

enum class Vertical : DirectionVector {
    TOP {
        override val dx: Int = 1
        override val dy: Int = 0
    },
    BOTTOM {
        override val dx: Int = -1
        override val dy: Int = 0
    },
}

enum class LeftDiagonal : DirectionVector {
    LEFT_TOP {
        override val dx: Int = 1
        override val dy: Int = -1
    },
    RIGHT_BOTTOM {
        override val dx: Int = -1
        override val dy: Int = 1
    },
}

enum class RightDiagonal : DirectionVector {
    TOP_RIGHT {
        override val dx: Int = 1
        override val dy: Int = 1
    },
    BOTTOM_LEFT {
        override val dx: Int = -1
        override val dy: Int = -1
    },
}
