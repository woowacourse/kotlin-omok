package omok.model.rule

import omok.model.stone.StoneType

object RenjuRuleAdapter {
//    fun checkRenjuRule(
//        board: Array<Array<StoneType>>,
//        row: Int,
//        col: Int,
//    ): PutResult {
//        val libBoard = changeBoardToLibBoard(board)
//        return ThreeThreeChecker.check(libBoard, row, col) ||
//                FourFourChecker.check(libBoard, row, col) ||
//                ExceedFiveChecker.check(libBoard, row, col)
//    }

    fun changeBoardToLibBoard(board: Array<Array<StoneType>>): Array<Array<Int>> {
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
