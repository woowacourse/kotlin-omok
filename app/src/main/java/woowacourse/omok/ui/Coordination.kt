package woowacourse.omok.ui

import woowacourse.omok.domain.board.Column
import woowacourse.omok.domain.board.Row

data class Coordination(
    val x: Column,
    val y: Row,
)
