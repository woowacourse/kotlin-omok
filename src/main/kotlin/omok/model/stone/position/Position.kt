package omok.model.stone.position

import omok.view.OutputView.Companion.BOARD_SIZE

data class Position(
    val row: Row,
    val col: Col,
) {
    init {
        require(row.value in MIN_RANGE until MIN_RANGE + BOARD_SIZE) { ERROR_ROW_RANGE }
        require(col.value in MIN_RANGE until MIN_RANGE + BOARD_SIZE) { ERROR_COL_RANGE }
    }

    companion object {
        private const val ERROR_ROW_RANGE = "가로 좌표는 오목판의 범위를 벗어날 수 없습니다"
        private const val ERROR_COL_RANGE = "세로 좌표는 오목판의 범위를 벗어날 수 없습니다"

        private const val MIN_RANGE = 0
    }
}
