package omok.domain.point

import omok.domain.board.OmokBoard
import omok.domain.board.StoneStatus

data class Point(
    val x: Int,
    val y: Int,
    val stoneStatus: StoneStatus,
) {
    init {
        require(x <= OmokBoard.MAX_COLUMN_SIZE) { ERR_OUT_OF_COLUMN }
        require(y <= OmokBoard.MAX_ROW_SIZE) { ERR_OUT_OF_ROW }
    }

    companion object {
        fun of(
            position: String,
            status: StoneStatus,
        ): Point {
            val x = OmokBoard.COLUMN_POOL.indexOf(position[0].uppercaseChar()) + 1
            val y = position.substring(1).toInt()
            return Point(x, y, status)
        }

        const val ERR_OUT_OF_COLUMN = "최대 열을 벗어납니다"
        const val ERR_OUT_OF_ROW = "최대 행을 벗어납니다"
    }
}
