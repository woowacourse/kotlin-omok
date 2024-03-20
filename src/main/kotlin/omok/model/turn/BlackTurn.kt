package omok.model.turn

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import omok.model.rule.FourFourRule
import omok.model.rule.OmokRule
import omok.model.rule.Rule
import omok.model.rule.SamSamRule
import omok.model.rule.SixMokRule

class BlackTurn(val board: Board) : Turn {
    private val prohibitedRules: List<Rule> = listOf(SamSamRule, FourFourRule, SixMokRule)

    override fun placeStone(point: Point): Turn {
        val stone = Stone(point, StoneColor.BLACK)

        board.place(stone)

        val isViolated =
            prohibitedRules.any {
                it.check(board)
            }
        if (isViolated) {
            board.removeStone(stone)
            return BlackTurn(board)
        }

        if (board.isFull() || OmokRule.check(board)) return Finished(StoneColor.BLACK)

        return WhiteTurn(board)
    }

    override fun color(): StoneColor {
        return StoneColor.BLACK
    }
}
