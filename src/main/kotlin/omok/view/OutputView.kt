package omok.view

import omok.domain.OmokBoard
import omok.domain.OmokLine
import omok.domain.StoneState

class OutputView {
    fun outputBoard(omokBoard: OmokBoard) {
        omokBoard.values.reversed().forEachIndexed { boardIdx, omokLine ->
            println(makeXLineString(boardIdx, omokLine))
        }
        println(board.last())
    }

    private fun makeXLineString(boardIdx: Int, omokLine: OmokLine): String {
        val stringBuilder = StringBuilder(board[boardIdx])
        omokLine.values.forEachIndexed { lineIdx, stoneState ->
            getStoneMark(stoneState)?.let { stringBuilder.setCharAt(LEFT_PADDING + lineIdx * BETWEEN_PADDING, it) }
        }
        return stringBuilder.toString()
    }

    private fun getStoneMark(stoneState: StoneState): Char? = when (stoneState) {
        StoneState.BLACK -> '●'
        StoneState.WHITE -> '○'
        else -> null
    }

    companion object {
        private const val LEFT_PADDING = 4
        private const val BETWEEN_PADDING = 3
        val board =
            listOf(
                " 15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐",
                " 14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
                " 13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
                " 12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
                " 11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
                " 10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
                "  9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
                "  8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
                "  7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
                "  6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
                "  5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
                "  4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
                "  3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
                "  2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
                "  1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘",
                "    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O",
            )
    }
}
