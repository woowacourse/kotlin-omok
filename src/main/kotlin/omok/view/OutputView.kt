package omok.view

import omok.model.Color
import omok.model.Stone

class OutputView {
    fun showGameStartHeader() {
        println("오목 게임을 시작합니다")
    }

    fun showBoard(stones: List<Stone>) {
        val boardRepresentation = initialBoardLayout.trimIndent().split(SPLIT_DIVIDER).toMutableList()

        stones.forEach { stone ->
            val rowIndex = (stone.point.row) * ROW_INTERVAL + ROW_PADDING
            val colIndex = BOARD_TOP_POSITION - stone.point.col
            boardRepresentation[colIndex] = boardRepresentation[colIndex].substring(BOARD_START_POSITION, rowIndex) +
                colorStone(stone.color) +
                boardRepresentation[colIndex].substring(rowIndex + NEXT)
        }

        boardRepresentation.forEach(::println)
    }

    private fun colorStone(color: Color) =
        when (color) {
            Color.BLACK -> '●'
            Color.WHITE -> '○'
        }

    fun showGameResult(turn: Color) {
        println("${if (turn == Color.WHITE) "백" else "흑"}의 승리입니다. 축하합니다.")
    }

    companion object {
        const val SPLIT_DIVIDER = "\n"
        const val ROW_INTERVAL = 3
        const val ROW_PADDING = 3
        const val BOARD_TOP_POSITION = 14
        const val BOARD_START_POSITION = 0
        const val NEXT = 1

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
    }
}
