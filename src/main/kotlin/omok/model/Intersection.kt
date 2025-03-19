package omok.model

import rule.wrapper.point.Point

data class Intersection(
    val point: Point,
    val state: IntersectionState,
)
