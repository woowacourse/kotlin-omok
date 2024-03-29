package omok.model.turn

import omok.model.Board
import omok.model.StoneAlreadyExists
import omok.model.StoneOutOfBoard
import omok.model.Success
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import omok.model.rule.FiveInRowRule
import omok.model.rule.FourByFourRule
import omok.model.rule.OverSixInRowRule
import omok.model.rule.Rule
import omok.model.rule.ThreeByThreeRule

class BlackTurn(board: Board) : Turn(board) {
    private val prohibitedRules: List<Rule> = listOf(ThreeByThreeRule, FourByFourRule, OverSixInRowRule)

    override fun placeStone(point: Point): Turn {
        val stone = Stone(point, StoneColor.BLACK)

        val nextBoard =
            when (val placeResult = board.place(stone)) {
                is StoneOutOfBoard, is StoneAlreadyExists -> return this
                is Success -> placeResult.board
            }

        val isViolated =
            prohibitedRules.any {
                it.check(nextBoard)
            }
        if (isViolated) {
            return this
        }

        if (nextBoard.isFull() || FiveInRowRule.check(nextBoard)) return Finished(nextBoard)

        return WhiteTurn(nextBoard)
    }

    override fun color(): StoneColor {
        return StoneColor.BLACK
    }
}
