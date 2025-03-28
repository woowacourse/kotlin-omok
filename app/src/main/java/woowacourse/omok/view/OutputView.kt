package woowacourse.omok.view

import woowacourse.omok.domain.Position
import woowacourse.omok.domain.StoneType

class OutputView {
    fun printStartMessage() {
        println(MESSAGE_START)
    }

    fun showBoard(customBoard: Array<Array<StoneType>>) {
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

    fun printFirstTurn() {
        println(MESSAGE_FIRST_TURN)
    }

    fun printNormalTurn(
        turnColor: StoneType,
        position: Position,
    ) {
        println(
            MESSAGE_TURN.format(
                if (turnColor == StoneType.BLACK) "백" else "흑",
                PositionParser.decode(position),
            ),
        )
    }

    private fun generateBoardArray(changedBoard: Array<Array<StoneType>>): Array<Array<String>> {
        val minimumX = 0
        val maximumX = changedBoard.size
        val minimumY = 0
        val maximumY = maximumX * 3 - 2

        val board = Array(maximumX) { Array(maximumY) { "─" } }

        for (i in changedBoard.indices) {
            for (j in 0 until changedBoard[i].size) {
                if (changedBoard[i][j] == StoneType.BLACK) board[j][i * 3] = "●"
                if (changedBoard[i][j] == StoneType.WHITE) board[j][i * 3] = "○"
            }
        }

        for (i in board.indices) {
            for (j in 0 until board[i].size) {
                if (board[i][j] == "●" || board[i][j] == "○") continue
                when {
                    i == minimumX && j == minimumY -> board[i][j] = "└"
                    i == maximumX - 1 && j == maximumY - 1 -> board[i][j] = "┐"
                    i == minimumX && j == maximumY - 1 -> board[i][j] = "┘"
                    i == maximumX - 1 && j == minimumY -> board[i][j] = "┌"
                    i == minimumX && j % 3 == 0 -> board[i][j] = "┴"
                    i == maximumX - 1 && j % 3 == 0 -> board[i][j] = "┬"
                    j == minimumY -> board[i][j] = "├"
                    j == maximumY - 1 -> board[i][j] = "┤"
                    j % 3 == 0 -> board[i][j] = "┼"
                }
            }
        }

        return board
    }

    fun showGameResult(stoneType: StoneType) {
        println(MESSAGE_WINNER_RESULT.format(stoneType.toText()))
    }

    fun print(lastTurn: StoneType) {
        println("${lastTurn.toText()}의 차례입니다.")
    }

    private fun StoneType.toText() =
        when (this) {
            StoneType.BLACK -> "흑"
            StoneType.WHITE -> "백"
            StoneType.EMPTY -> ""
        }

    companion object {
        private const val MESSAGE_START = "오목 게임을 시작합니다."
        private const val MESSAGE_FIRST_TURN = "흑의 차례입니다."
        private const val MESSAGE_TURN = "%s의 차례입니다. (마지막 돌의 위치: %s)"
        private const val MESSAGE_WINNER_RESULT = "%s의 승리입니다. 축하합니다."
        private const val ROW_INDEX = "   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O"
    }
}
