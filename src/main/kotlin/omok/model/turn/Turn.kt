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
    protected open val additionalProhibitedRules: List<Rule> = listOf()

    fun nextTurn(point: Point): Turn {
        val stone = Stone(point, color())
        val isViolated = (baseProhibitedRules + additionalProhibitedRules).any { it.check(stone, board) }
        if (isViolated) {
            return this
        }
        val nextBoard = board.place(stone)
        return destination(nextBoard)
    }

    protected fun isWin(stone: Stone): Boolean {
        return FiveInRowRule.check(stone, board)
    }

    abstract fun isWin(): Boolean

    fun isDraw(): Boolean {
        return board.isFull()
    }

    protected abstract fun destination(board: Board): Turn

    abstract fun color(): StoneColor
}
