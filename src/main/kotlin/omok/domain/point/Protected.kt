package omok.domain.point

import omok.domain.board.OmokBoard

@Suppress("functionName")
fun Protected(position: String): Protected {
    val x = OmokBoard.COLUMN_POOL.indexOf(position[0].uppercaseChar()) + 1
    val y = position.substring(1).toInt()
    return Protected(x, y)
}

data class Protected(val x1: Int, val y1: Int) : Point(x1, y1) {
    override fun toggle(position: String): Point {
        return this
    }
}
