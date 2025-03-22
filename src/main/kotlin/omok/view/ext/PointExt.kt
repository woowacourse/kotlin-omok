package omok.view.ext

import omok.domain.point.Point
import omok.domain.point.Point.Companion.COLUMN_POOL

fun Point.position(): String {
    val dx = COLUMN_POOL[this.x - 1].toString()
    val dy = this.y
    return dx + dy
}
