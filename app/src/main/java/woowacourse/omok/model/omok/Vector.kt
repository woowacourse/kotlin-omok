package woowacourse.omok.model.omok

enum class Vector(val position: Position) {
    DiagonalUpRight(Position(1, 1)),
    DiagonalDownRight(Position(1, -1)),
    Up(Position(0, 1)),
    Right(Position(1, 0)),
}
