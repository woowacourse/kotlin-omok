import domain.MIN_VIEW_X
import domain.MIN_VIEW_Y

object PointAdapter {
    fun create(x: Char, y: Int): Pair<Int, Int> {
        return Pair(x.uppercase()[0] - MIN_VIEW_X, y - MIN_VIEW_Y)
    }
}