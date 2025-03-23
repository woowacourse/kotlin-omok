package omok.mapper

import omok.model.stone.position.Position
import rule.wrapper.point.Point

class PointMapper {
    fun from(position: Position): Point = Point(position.row.value, position.col.value)
}
