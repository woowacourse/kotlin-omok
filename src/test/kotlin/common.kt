import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone
fun Stone(x: Int, y: Int, color: Color = Color.White): Stone = Stone(Point(x, y), color)
