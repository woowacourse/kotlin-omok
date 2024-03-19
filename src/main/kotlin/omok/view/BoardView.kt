package omok.view

import omok.model.Board
import omok.model.StoneType

object BoardView {
    private const val BLACK_STONE = '●'
    private const val WHITE_STONE = '○'

    private var initialBoardLayout =
        """
        15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
        14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
        10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
         9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
         8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
         7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
         6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
         5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
         4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
         3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
         2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
         1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
           A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
        """.trimIndent()

    private const val BASE_POSITION = 661
    private const val INTERVAL_X = 3
    private const val INTERVAL_Y = 47

    fun printBoard(board: Board) {
        val stringBuilder = StringBuilder(initialBoardLayout)
        board.layout.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, stoneType ->
                val index = calculatePositionIndex(rowIndex, columnIndex)
                getStoneSymbol(stoneType)?.apply {
                    stringBuilder.setCharAt(index, this)
                }
            }
        }
        println(stringBuilder)
    }

    private fun calculatePositionIndex(
        rowIndex: Int,
        colIndex: Int,
    ): Int {
        return BASE_POSITION + (rowIndex * INTERVAL_X) - (colIndex * INTERVAL_Y)
    }

    private fun getStoneSymbol(stoneType: StoneType): Char? {
        return when (stoneType) {
            StoneType.BLACK_STONE -> BLACK_STONE
            StoneType.WHITE_STONE -> WHITE_STONE
            StoneType.EMPTY -> null
        }
    }
}
