package woowacourse.omok.model.board

import woowacourse.omok.model.board.OmokBoardConfig.Y_MAX_RANGE
import woowacourse.omok.model.board.OmokBoardConfig.Y_MIN_RANGE

data class Y(
    val point: Int,
) {
    init {
        require(point in Y_MIN_RANGE..Y_MAX_RANGE) { "좌표의 범위는 1부터 15까지 입니다." }
    }

    operator fun plus(other: Int) = Y(Y_MIN_RANGE + (this.point - Y_MIN_RANGE) + other)

    operator fun minus(other: Int) = Y(Y_MIN_RANGE + (this.point - Y_MIN_RANGE) - other)
}
