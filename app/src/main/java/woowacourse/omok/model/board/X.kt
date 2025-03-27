package woowacourse.omok.model.board

import woowacourse.omok.model.board.OmokBoardConfig.X_MAX_RANGE
import woowacourse.omok.model.board.OmokBoardConfig.X_MIN_RANGE

data class X(
    val point: Int,
) {
    init {
        require(point in X_MIN_RANGE..X_MAX_RANGE) { "좌표의 범위는 1부터 15까지 입니다." }
    }

    operator fun plus(other: Int) = X(X_MIN_RANGE + (this.point - X_MIN_RANGE) + other)

    operator fun minus(other: Int) = X(X_MIN_RANGE + (this.point - X_MIN_RANGE) - other)
}
