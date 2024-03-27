import omok.model.Board
import omok.model.Either
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor

fun Board.testPlaceStone(stone: Stone): Board {
    return (this.place(stone) as Either.Right).value
}

fun Board.place(
    x: Int,
    y: Int,
): Board {
    val stone = Stone(Point(x, y), StoneColor.BLACK)
    return (this.place(stone) as Either.Right).value
}

fun Board.place(
    x: Int,
    y: Int,
    color: StoneColor,
): Board {
    val stone = Stone(Point(x, y), color)
    return (this.place(stone) as Either.Right).value
}
