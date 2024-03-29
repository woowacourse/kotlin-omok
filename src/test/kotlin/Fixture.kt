import omok.model.Board
import omok.model.StoneAlreadyExists
import omok.model.StoneOutOfBoard
import omok.model.Success
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor

fun Board.testPlaceStone(stone: Stone): Board {
    return when (val result = this.place(stone)) {
        is StoneOutOfBoard -> throw IllegalStateException("범위 벗어남")
        is StoneAlreadyExists -> throw IllegalStateException("돌 중복")
        is Success -> result.board
    }
}

fun Board.place(
    x: Int,
    y: Int,
): Board {
    val stone = Stone(Point(x, y), StoneColor.BLACK)
    return when (val result = this.place(stone)) {
        is StoneOutOfBoard -> throw IllegalStateException("범위 벗어남")
        is StoneAlreadyExists -> throw IllegalStateException("돌 중복")
        is Success -> result.board
    }
}

fun Board.place(
    x: Int,
    y: Int,
    color: StoneColor,
): Board {
    val stone = Stone(Point(x, y), color)
    return when (val result = this.place(stone)) {
        is StoneOutOfBoard -> throw IllegalStateException("범위 벗어남")
        is StoneAlreadyExists -> throw IllegalStateException("돌 중복")
        is Success -> result.board
    }
}
