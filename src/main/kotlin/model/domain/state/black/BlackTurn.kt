package model.domain.state.black

import model.domain.rule.OmokForbiddenRule
import model.domain.state.Omok
import model.domain.state.State
import model.domain.state.Turn
import model.domain.state.white.WhiteTurn
import model.domain.tools.Board
import model.domain.tools.Stone

class BlackTurn : Turn() {
    override val stoneColor: Stone
        get() = Stone.BLACK
    override val omok: Omok
        get() = BlackOmok(stoneColor)
    override val turn: Turn
        get() = BlackTurn()
    override val nextTurn: State
        get() = WhiteTurn()

    override fun isForbidden(board: Board, x: Int, y: Int): Boolean =
        (is44(board, x, y) || is33(board, x, y))

    private fun is44(board: Board, x: Int, y: Int): Boolean =
        OmokForbiddenRule(board, stoneColor).countOpenFours(x, y) >= LIMIT_RULE_44

    private fun is33(board: Board, x: Int, y: Int): Boolean =
        OmokForbiddenRule(board, stoneColor).countOpenThrees(x, y) >= LIMIT_RULE_33

    companion object {
        private const val LIMIT_RULE_44 = 2
        private const val LIMIT_RULE_33 = 2
    }
}
