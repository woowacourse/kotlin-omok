package omok.model.rule

import omok.model.Board
import omok.model.Position
import omok.model.Stone
import omok.model.rule.library.FourFourRule
import omok.model.rule.library.ThreeThreeRule

object RuleAdapter {
    fun abideDoubleFourRule(
        stone: Stone,
        board: Board,
        position: Position,
    ): Boolean {
        return FourFourRule(stone.convert(), stone.next().convert()).abide(board.convert(), position.convert())
    }

    fun abideDoubleOpenThreeRule(
        stone: Stone,
        board: Board,
        position: Position,
    ): Boolean {
        return ThreeThreeRule(stone.convert(), stone.next().convert()).abide(board.convert(), position.convert())
    }

    private fun Board.convert(): List<List<Int>> {
        val array = Array(15) { IntArray(15) }
        board.forEach { (position, stone) ->
            array[position.col][position.row] =
                stone.convert()
        }
        return array.map { it.toList() }.toList()
    }

    private fun Stone.convert(): Int =
        when (this) {
            Stone.BLACK -> 1
            Stone.WHITE -> 2
            Stone.NONE -> 0
        }

    private fun Position.convert(): Pair<Int, Int> = row to col
}
