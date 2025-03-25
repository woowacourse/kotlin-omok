package omok.domain.ext

import omok.domain.board.OmokColumn
import omok.domain.point.Point

fun Point.toCoordination(): String {
    val dx = OmokColumn.find(this.x.value).name
    val dy = this.y.value
    return dx + dy
}
