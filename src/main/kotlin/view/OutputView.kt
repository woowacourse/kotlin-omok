package view

import domain.board.Board
import domain.stone.Color

class OutputView {
    fun printGameStartMessage() {
        println(GAME_START_MESSAGE)
    }

    fun printOmokBoardState(board: Board) {
        for (i in INITIAL_ROW..END_ROW) {
            val frontNumber = BOARD[i].substring(0, COL_UNIT)
            val line = BOARD[i].substring(COL_UNIT)
            val blackStonesPoint = board.filterPointY(Color.BLACK, i)
            val whiteStonesPoint = board.filterPointY(Color.WHITE, i)
            print(frontNumber + makeBoardLine(line, blackStonesPoint, whiteStonesPoint))
        }
        println(BOARD.last())
    }

    private fun Board.filterPointY(color: Color, curY: Int): List<Int> {
        return placedStones
            .filter { it.point.y + 1 == END_COL - curY && it.color == color }
            .map { stone -> (stone.point.x) * COL_UNIT }
    }

    private fun makeBoardLine(line: String, blackStonesPosition: List<Int>, whiteStonesPosition: List<Int>): String =
        buildString {
            append(line)
            whiteStonesPosition.forEach { x ->
                replace(x, x + 1, WHITE_STONE)
            }
            blackStonesPosition.forEach { x ->
                replace(x, x + 1, BLACK_STONE)
            }
        }

    fun printWinner(color: Color) {
        println(WINNER.format(color))
    }

    companion object {
        private const val COL_UNIT = 3
        private const val END_COL = 15
        private const val INITIAL_ROW = 0
        private const val END_ROW = 14
        private const val BLACK_STONE = "●"
        private const val WHITE_STONE = "◎"

        /**
         * TODO: board 를 하드코딩 하기보다, 구성하는 로직을 따로 만들어 보는 것은 어떨까요?
         * board 를 그리는 규칙이 바뀔 때마다 해당 값의 변경사항이 많이 발생하고 유지보수가 어려워질 것 같아요.
         */
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

        private const val GAME_START_MESSAGE = "오목 게임을 시작합니다."
        private const val WINNER = "승리자: %s"
    }
}
