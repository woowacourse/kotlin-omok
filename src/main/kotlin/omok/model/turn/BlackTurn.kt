package omok.model.turn

import omok.model.Board
import omok.model.entity.StoneColor
import omok.model.rule.FourByFourRule
import omok.model.rule.OverSixInRowRule
import omok.model.rule.Rule
import omok.model.rule.ThreeByThreeRule

class BlackTurn(board: Board) : Turn(board) {
    override val prohibitedRules: List<Rule> =
        listOf(ThreeByThreeRule, FourByFourRule, OverSixInRowRule)

    override fun destination(board: Board): Turn {
        return WhiteTurn(board)
    }

    override fun color(): StoneColor {
        return StoneColor.BLACK
    }
}
