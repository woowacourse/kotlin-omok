package omok.model.rule

import lib.renjurule.ExceedFiveChecker
import lib.renjurule.FourFourChecker
import lib.renjurule.ThreeThreeChecker
import omok.model.stone.StoneType

object RenjuRuleAdapter {
    fun checkRenjuRule(
        board: Array<Array<StoneType>>,
        row: Int,
        col: Int,
    ): Boolean {
        val libBoard = changeBoardToLibBoard(board)
        return ThreeThreeChecker.checkThreeThree(libBoard, row, col) ||
            FourFourChecker.checkFourFour(libBoard, row, col) ||
            ExceedFiveChecker.checkMoreThanFive(libBoard, row, col)
    }

    private fun changeBoardToLibBoard(board: Array<Array<StoneType>>): Array<Array<Int>> {
        val libBoard = Array(board.size) { Array(board.size) { 0 } }
        for (i in board.indices) {
            changeStoneValue(board, libBoard, i)
        }
        return libBoard
    }

    private fun changeStoneValue(
        board: Array<Array<StoneType>>,
        libBoard: Array<Array<Int>>,
        i: Int,
    ) {
        for (j in board.indices) {
            libBoard[i][j] = board[i][j].changeToLibBoardValue()
        }
    }

    fun StoneType.changeToLibBoardValue() =
        when (this) {
            StoneType.NONE -> 0
            StoneType.BLACK_STONE -> 1
            StoneType.WHITE_STONE -> 2
        }
}
