package omok.domain.place

import omok.domain.board.OmokBoard

sealed class Place(val x: Int, val y: Int) {
    init {
        require(x <= OmokBoard.MAX_COLUMN_SIZE) { ERR_OUT_OF_COLUMN }
        require(y <= OmokBoard.MAX_ROW_SIZE) { ERR_OUT_OF_ROW }
    }

    abstract fun toggle(position: String): Place

    abstract fun opponent(): Place

    companion object {
        const val DUMMY_POSITION = -100
        const val ERR_OUT_OF_COLUMN = "최대 열을 벗어납니다"
        const val ERR_OUT_OF_ROW = "최대 행을 벗어납니다"
        const val ERR_INVALID_VALUE = "잘못된 값을 입력하셨습니다"
    }
}
