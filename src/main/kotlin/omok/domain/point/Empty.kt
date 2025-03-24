package omok.domain.point

import omok.domain.board.OmokBoard

@Suppress("functionName")
fun Empty(position: String): Empty {
    val x = OmokBoard.COLUMN_POOL.indexOf(position[0].uppercaseChar()) + 1
    val y = position.substring(1).toInt()
    return Empty(x, y)
}

data class Empty(override val x: Int, override val y: Int) : Point2() {
    override fun toggle(position: String): Point2 {
        return this
    }

    init {
        require(x <= OmokBoard.MAX_COLUMN_SIZE) { ERR_OUT_OF_COLUMN }
        require(y <= OmokBoard.MAX_ROW_SIZE) { ERR_OUT_OF_ROW }
    }

    companion object {
        const val ERR_OUT_OF_COLUMN = "최대 열을 벗어납니다"
        const val ERR_OUT_OF_ROW = "최대 행을 벗어납니다"
    }
}
