package omok.domain.point

import omok.domain.board.OmokBoard
import omok.domain.point.Point.Companion.ERR_INVALID_VALUE
import java.lang.IllegalArgumentException

@Suppress("functionName")
fun Empty(position: String): Empty {
    return runCatching {
        val x = OmokBoard.COLUMN_POOL.indexOf(position[0].uppercaseChar()) + 1
        require(x != 0) { ERR_INVALID_VALUE }
        val y = position.substring(1).toInt()
        Empty(x, y)
    }.getOrElse { throw IllegalArgumentException("잘못된 값을 입력하셨습니다") }
}

data class Empty(val x1: Int, val y1: Int) : Point(x1, y1) {
    override fun toggle(position: String): Point {
        return Black(position)
    }

    override fun opponent(): Point {
        return Black(DUMMY_POSITION, DUMMY_POSITION)
    }

    companion object {
        fun dummy(): Empty = Empty(DUMMY_POSITION, DUMMY_POSITION)
    }
}
