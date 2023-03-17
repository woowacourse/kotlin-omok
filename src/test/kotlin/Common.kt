import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

fun Stone(x: Int, y: Int, color: Color = Color.BLACK) = Stone(Position(x, y), color)
