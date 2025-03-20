package omok.view.ext

import omok.domain.board.OmokColumn
import omok.domain.point.Point

fun Point.position(): String {
    val dx = OmokColumn.find(this.x.value).name
    val dy = this.y.value
    return dx + dy
}
