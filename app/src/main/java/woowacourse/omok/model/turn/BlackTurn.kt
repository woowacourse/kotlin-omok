package omok.model.turn

import omok.model.Board
import omok.model.entity.StoneColor
import omok.model.rule.FourByFourRule
import omok.model.rule.OverSixInRowRule
import omok.model.rule.Rule
import omok.model.rule.ThreeByThreeRule
import java.lang.IllegalStateException

class BlackTurn(board: Board) : Turn(board) {
    override val additionalProhibitedRules: List<Rule> =
        listOf(ThreeByThreeRule, FourByFourRule, OverSixInRowRule)

    override fun destination(board: Board): Turn {
        val stone = board.previousStone() ?: throw IllegalStateException()
        if (isWin(stone) || isDraw()) {
            return Finished(board)
        }
        return WhiteTurn(board)
    }

    override fun isWin(): Boolean = false

    override fun color(): StoneColor {
        return StoneColor.BLACK
    }
}
