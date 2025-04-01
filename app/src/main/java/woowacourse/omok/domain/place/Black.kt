package omok.domain.place

import omok.domain.board.OmokBoard
import omok.domain.place.Place.Companion.ERR_INVALID_VALUE
import java.lang.IllegalArgumentException

@Suppress("functionName")
fun Black(position: String): Black {
    return runCatching {
        val x = OmokBoard.COLUMN_POOL.indexOf(position[0].uppercaseChar()) + 1
        require(x != 0) { ERR_INVALID_VALUE }
        val y = position.substring(1).toInt()
        Black(x, y)
    }.getOrElse { throw IllegalArgumentException(ERR_INVALID_VALUE) }
}

data class Black(val x1: Int, val y1: Int) : Place(x1, y1) {
    override fun toggle(position: String): Place {
        return White(position)
    }

    override fun opponent(): Place {
        return White(DUMMY_POSITION, DUMMY_POSITION)
    }
}
