package domain.stone

import rule.wrapper.point.Point

class Stone(
    val point: Point,
    val type: StoneType,
) {
    companion object {
        fun from(point: Point, type: StoneType): Stone =
            Stone(point, type)
    }
}
