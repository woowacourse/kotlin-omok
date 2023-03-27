
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

fun Stone(x: Int, y: Int, color: Color = Color.BLACK) = Stone(Position(x, y), color)

private val POSITIONS: List<Position> = Position.all()

fun List<Stone>.convertToBoard(): Map<Position, Color?> {
    val map: MutableMap<Position, Color?> = POSITIONS.associateWith { null }.toMutableMap()
    forEach {
        map[it.position] = it.color
    }
    return map
}
