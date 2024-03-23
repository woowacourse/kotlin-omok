package omok.model.rule

import omok.model.Board
import omok.model.Position
import omok.model.Stone
import omok.model.rule.library.OmokRule

object RuleAdapter {
    fun abideRule(
        omokRule: OmokRule,
        board: Board,
        position: Position,
    ): Boolean {
        return omokRule.abide(board.convert(), position.convert())
    }

    private fun Board.convert(): List<List<Int>> {
        val array = Array(size) { IntArray(size) }
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
