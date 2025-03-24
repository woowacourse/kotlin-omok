package omok.domain.point

import omok.domain.board.OmokBoard
import java.lang.IllegalArgumentException

@Suppress("functionName")
fun White(position: String): White {
    return runCatching {
        val x = OmokBoard.COLUMN_POOL.indexOf(position[0].uppercaseChar()) + 1
        val y = position.substring(1).toInt()
        White(x, y)
    }.getOrElse { throw IllegalArgumentException("잘못된 값을 입력하셨습니다") }
}

data class White(val x1: Int, val y1: Int) : Point(x1, y1) {
    override fun toggle(position: String): Point {
        return Black(position)
    }
}
