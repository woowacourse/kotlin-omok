package omok.model.turn

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import omok.model.rule.AlreadyExistRule
import omok.model.rule.FiveInRowRule
import omok.model.rule.OutOfBoardRule
import omok.model.rule.Rule

abstract class Turn(val board: Board) {
    private val baseProhibitedRules: List<Rule> = listOf(OutOfBoardRule, AlreadyExistRule)
    protected abstract val prohibitedRules: List<Rule>

    fun nextTurn(point: Point): Turn {
        val stone = Stone(point, color())
        val isViolated = (baseProhibitedRules + prohibitedRules).any { it.check(stone, board) }
        if (isViolated) {
            return this
        }
        val nextBoard = board.place(stone)
        if (nextBoard.isFull() || FiveInRowRule.check(stone, board)) return Finished(nextBoard)
        return destination(nextBoard)
    }

    protected abstract fun destination(board: Board): Turn

    abstract fun color(): StoneColor
}
