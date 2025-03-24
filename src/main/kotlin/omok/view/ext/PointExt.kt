package omok.view.ext

import omok.domain.board.OmokBoard
import omok.domain.point.Point2

fun Point2.position(): String {
    val dx = OmokBoard.COLUMN_POOL[this.x - 1].toString()
    val dy = this.y
    return dx + dy
}
