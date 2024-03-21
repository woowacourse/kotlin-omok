package omok.model

import omok.model.rule.BlackWinRule
import omok.model.rule.FourFourRule
import omok.model.rule.ThreeThreeRule
import omok.model.rule.WhiteWinRule

object RuleAdapter {
    fun isBlackWin(board: Board, position: Position): Boolean {
        return BlackWinRule.validate(board.convert(), position.convert())
    }

    fun isWhiteWin(board: Board, position: Position): Boolean {
        return WhiteWinRule.validate(board.convert(), position.convert())
    }

    fun violateDoubleFour(board: Board, position: Position): Boolean {
        return FourFourRule.validate(board.convert(), position.convert())
    }

    fun violateDoubleOpenThree(board: Board, position: Position): Boolean {
        return ThreeThreeRule.validate(board.convert(), position.convert())
    }

    private fun Board.convert(): List<List<Int>> {
        val array = Array(15) { IntArray(15) }
        board.forEach { (position, stone) ->
            array[position.row][position.col] =
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