package omok.view.ext

import omok.domain.board.OmokBoard
import omok.domain.point.Point

fun Point.position(): String {
    val dx = OmokBoard.COLUMN_POOL[this.x - 1].toString()
    val dy = this.y
    return dx + dy
}
