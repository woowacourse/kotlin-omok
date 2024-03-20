import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.StoneColor

fun Board.place(
    x: Int,
    y: Int,
) {
    this.place(Point(x, y), StoneColor.BLACK)
}
