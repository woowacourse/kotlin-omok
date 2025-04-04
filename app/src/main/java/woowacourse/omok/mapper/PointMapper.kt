package woowacourse.omok.mapper

import omok.model.stone.position.Position
import rule.wrapper.point.Point

fun interface PointMapper {
    fun from(position: Position): Point
}
