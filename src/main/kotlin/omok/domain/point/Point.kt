package omok.domain.point

import omok.domain.board.OmokBoard

sealed class Point(val x: Int, val y: Int) {
    init {
        require(x <= OmokBoard.MAX_COLUMN_SIZE) { ERR_OUT_OF_COLUMN }
        require(y <= OmokBoard.MAX_ROW_SIZE) { ERR_OUT_OF_ROW }
    }

    abstract fun toggle(position: String): Point

    abstract fun opponent(): Point

    companion object {
        const val DUMMY_POSITION = -100
        const val ERR_OUT_OF_COLUMN = "최대 열을 벗어납니다"
        const val ERR_OUT_OF_ROW = "최대 행을 벗어납니다"
    }
}
