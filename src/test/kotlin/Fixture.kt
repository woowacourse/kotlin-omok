import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor

fun Board.place(
    x: Int,
    y: Int,
) {
    val stone = Stone(Point(x, y), StoneColor.BLACK)
    this.place(stone)
}

fun Board.place(
    x: Int,
    y: Int,
    color: StoneColor,
) {
    val stone = Stone(Point(x, y), color)
    this.place(stone)
}
