package woowacourse.omok.domain.point

import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.Column
import woowacourse.omok.domain.board.Row

data class Point(
    val x: Column,
    val y: Row,
    val status: BoardStatus,
) {
    companion object {
        val WALL = Point(Column(-1), Row(-1), BoardStatus.Empty)
    }
}
