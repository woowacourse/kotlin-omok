package omok.view

import omok.domain.OmokBoard
import omok.domain.state.BlackStoneState
import omok.domain.state.StoneState
import omok.domain.state.WhiteStoneState

class OutputView {
    fun outputInit() {
        println("오목 게임을 시작합니다.")
    }

    fun outputBoard(omokBoard: OmokBoard) {
        val stringBuilder = StringBuilder(board)
        omokBoard.value.keys.forEach { point ->
            getStoneMark(omokBoard[point])?.let {
                stringBuilder.setCharAt(LEFT_PADDING + (point.x.value * BETWEEN_PADDING) + (15 - point.y.value) * NEXT_PADDING, it)
            }
        }
        println(stringBuilder.toString())
    }

    private fun getStoneMark(stoneState: StoneState): Char? = when (stoneState) {
        BlackStoneState -> '●'
        WhiteStoneState -> '○'
        else -> null
    }

    companion object {
        private const val LEFT_PADDING = 1
        private const val NEXT_PADDING = 48
        private const val BETWEEN_PADDING = 3
        val board =
            " 15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐\n" +
                " 14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                " 13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                " 12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                " 11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                " 10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "  9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "  8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "  7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "  6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "  5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "  4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "  3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "  2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "  1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘\n" +
                "    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O"
    }
}
