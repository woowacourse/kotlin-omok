package omok.model.rule

import omok.model.Board
import omok.model.Position
import omok.model.Stone
import omok.model.rule.library.FourFourRule
import omok.model.rule.library.OverlineRule
import omok.model.rule.library.ThreeThreeRule

object RuleAdapter {
    fun abideDoubleFourRule(
        board: Board,
        position: Position,
    ): Boolean {
        return FourFourRule.abide(board.convert(), position.convert())
    }

    fun abideDoubleOpenThreeRule(
        board: Board,
        position: Position,
    ): Boolean {
        return ThreeThreeRule.abide(board.convert(), position.convert())
    }

    fun abideOverLineRule(
        board: Board,
        position: Position,
    ): Boolean {
        return OverlineRule.abide(board.convert(), position.convert())
    }

    private fun Board.convert(): List<List<Int>> {
        val array = Array(15) { IntArray(15) }
        board.forEach { (position, stone) ->
            array[position.col][position.row] =
                when (stone) {
                    Stone.BLACK -> 1
                    Stone.WHITE -> 2
                    Stone.NONE -> 0
                }
        }
        return array.map { it.toList() }.toList()
    }

    private fun Position.convert(): Pair<Int, Int> = row to col
}
