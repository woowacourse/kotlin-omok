import domain.Board.Companion.MIN_VIEW_X
import domain.Board.Companion.MIN_VIEW_Y
import domain.stone.Point

object PointAdapter {
    fun create(x: Char, y: Int): Point {
        return Point(x.uppercase()[0] - MIN_VIEW_X, y - MIN_VIEW_Y)
    }
}