package omok.external.mapper

import omok.model.stone.position.Position
import rule.wrapper.point.Point

fun Position.toPoint(): Point = Point(this.row.value, this.col.value)
