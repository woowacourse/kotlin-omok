package woowacourse.omok.domain.model

import woowacourse.omok.domain.model.rule.RuleAdapter

class Player(val stone: Stone, private val rules: RuleAdapter) {
    fun canPlace(
        board: Board,
        position: Position,
    ): Boolean = rules.validPosition(board, position)

    fun isWin(
        board: Board,
        position: Position,
    ): Boolean = rules.isWin(board, position)
}
