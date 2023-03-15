class OutputView {

    fun printOmokBoardState(board: Board) {
        for (i in 0..14) {
            val frontNumber = BOARD[i].substring(0, 3)
            val line = BOARD[i].substring(3)
            val blackStonesPoint = board.filterPointY(Color.BLACK, i)
            val whiteStonesPoint = board.filterPointY(Color.WHITE, i)
            print(frontNumber + makeBoardLine(line, blackStonesPoint, whiteStonesPoint))
        }
        println(BOARD.last())
    }

    private fun Board.filterPointY(color: Color, curY: Int): List<Int> {
        return getStones()
            .filter { it.point.y == 15 - curY && it.color == color }
            .map { stone -> (stone.point.x - 1) * 3 }
    }

    private fun makeBoardLine(line: String, blackStonesPoint: List<Int>, whiteStonesPoint: List<Int>): String =
        buildString {
            append(line)
            whiteStonesPoint.forEach { x ->
                replace(x, x + 1, "◎")
            }
            blackStonesPoint.forEach { x ->
                replace(x, x + 1, "●")
            }
        }

    fun printWinner(color: Color) {
        println(PRINT_WINNER.format(color))
    }

    companion object {
        private const val BLACK_STONE = '●'
        private const val WHITE_STONE = '◎'

        private val BOARD = listOf(
            "15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐\n",
            "14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            "13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            "12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            "11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            "10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n",
            " 1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘\n",
            "   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O"
        )

        private const val PRINT_WINNER = "승리자: %s"
    }
}
