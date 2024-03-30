package omok.model.turn

import omok.model.Board
import omok.model.entity.StoneColor
import omok.model.rule.Rule

class Finished(board: Board) : Turn(board) {
    override val prohibitedRules: List<Rule> = listOf()

    override fun destination(board: Board): Turn {
        throw IllegalStateException()
    }

    override fun color(): StoneColor {
        val stone = board.previousStone() ?: return StoneColor.BLACK
        return stone.stoneColor
    }
}
