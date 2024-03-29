package woowacourse.omok.model.rule

import woowacourse.omok.model.board.Board

interface Rule {
    fun check(board: Board): Boolean
}
