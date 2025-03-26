package woowacourse.omok.domain.grid

import woowacourse.omok.domain.grid.OmokGrid.Companion.DEFAULT_SIZE
import woowacourse.omok.domain.grid.OmokGrid.Companion.MIN_BOUND

data class Point(val row: Row, val col: Column) {
    init {
        check(row.value in MIN_BOUND..DEFAULT_SIZE) { ERROR_OUT_OF_BOUNDS }
        check(col.value in MIN_BOUND..DEFAULT_SIZE) { ERROR_OUT_OF_BOUNDS }
    }

    companion object {
        private const val ERROR_OUT_OF_BOUNDS = "오목판 밖에 돌을 둘 수 없습니다"
    }
}
