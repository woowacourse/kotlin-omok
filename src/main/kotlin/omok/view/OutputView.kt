package omok.view

import omok.model.Turn

class OutputView {
    fun showGameStartHeader() {
        println(HEADER_GAME_START)
    }

    fun showBoard(customBoard: Array<Array<Int>>) {
        val board = generateBoardArray(customBoard)

        for (row in board.size - 1 downTo 0) {
            print("${(row + 1).toString().padStart(2)} ")
            for (col in 0 until board[row].size) {
                print(board[row][col])
            }
            println()
        }
        println(ROW_INDEX)
    }

    private fun generateBoardArray(customBoard: Array<Array<Int>>): Array<Array<String>> {
        val minX = 0
        val maxX = customBoard.size
        val minY = 0
        val maxY = maxX * 3 - 2

        val board = Array(maxX) { Array(maxY) { "─" } }

        for (i in customBoard.indices) {
            for (j in 0 until customBoard[i].size) {
                if (customBoard[i][j] == 1) board[j][i * 3] = "●"
                if (customBoard[i][j] == 2) board[j][i * 3] = "○"
            }
        }

        for (i in board.indices) {
            for (j in 0 until board[i].size) {
                if (board[i][j] == "●" || board[i][j] == "○") continue
                when {
                    i == minX && j == minY -> board[i][j] = "└"
                    i == maxX - 1 && j == maxY - 1 -> board[i][j] = "┐"
                    i == minX && j == maxY - 1 -> board[i][j] = "┘"
                    i == maxX - 1 && j == minY -> board[i][j] = "┌"
                    i == minX && j % 3 == 0 -> board[i][j] = "┴"
                    i == maxX - 1 && j % 3 == 0 -> board[i][j] = "┬"
                    j == minY -> board[i][j] = "├"
                    j == maxY - 1 -> board[i][j] = "┤"
                    j % 3 == 0 -> board[i][j] = "┼"
                }
            }
        }

        return board
    }

    fun showGameResult(turn: Turn) {
        println(HEADER_WINNER_RESULT.format(if (turn.isWhite()) "백" else "흑"))
    }

    companion object {
        private const val HEADER_GAME_START = "오목 게임을 시작합니다"
        private const val ROW_INDEX = "   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O"
        private const val HEADER_WINNER_RESULT = "%s의 승리입니다. 축하합니다."
    }
}
