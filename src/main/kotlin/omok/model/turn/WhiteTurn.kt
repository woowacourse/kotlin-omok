package omok.model.turn

import omok.model.Board
import omok.model.entity.StoneColor
import omok.model.rule.Rule

class WhiteTurn(board: Board) : Turn(board) {
    override val prohibitedRules: List<Rule> = listOf()

    override fun destination(board: Board): Turn {
        return BlackTurn(board)
    }

    override fun color(): StoneColor {
        return StoneColor.WHITE
    }
}
