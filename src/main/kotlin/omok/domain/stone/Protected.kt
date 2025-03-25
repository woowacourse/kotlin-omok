package omok.domain.stone

import omok.domain.board.OmokBoard
import omok.domain.stone.Stone.Companion.ERR_INVALID_VALUE
import java.lang.IllegalArgumentException

@Suppress("functionName")
fun Protected(position: String): Protected {
    return runCatching {
        val x = OmokBoard.COLUMN_POOL.indexOf(position[0].uppercaseChar()) + 1
        require(x != 0) { ERR_INVALID_VALUE }
        val y = position.substring(1).toInt()
        Protected(x, y)
    }.getOrElse { throw IllegalArgumentException("잘못된 값을 입력하셨습니다") }
}

data class Protected(val x1: Int, val y1: Int) : Stone(x1, y1) {
    override fun toggle(position: String): Stone {
        return this
    }

    override fun opponent(): Stone {
        return this
    }

    companion object {
        fun dummy(): Protected = Protected(DUMMY_POSITION, DUMMY_POSITION)
    }
}
