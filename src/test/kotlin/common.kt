import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone
fun Stone(x: Int, y: Int, color: Color = Color.WHITE): Stone = Stone(Point(x - 1, y - 1), color)

fun createPoint(x: Int, y: Int): Point = domain.stone.Point(x - 1, y - 1)
