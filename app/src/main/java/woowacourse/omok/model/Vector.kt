package woowacourse.omok.model

enum class Vector(val x: Int, val y: Int) {
    DiagonalUpRight(1, 1),
    DiagonalDownRight(1, -1),
    Up(0, 1),
    Right(1, 0),
}
