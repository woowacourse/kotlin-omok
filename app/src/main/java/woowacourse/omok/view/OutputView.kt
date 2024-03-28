package woowacourse.omok.view

import Controller.omok
import woowacourse.omok.model.board.CoordsNumber
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.omokGame.GameEventListener

private const val COLUMN_STRING = "   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O"
private const val X = "X"
private const val EMPTY = "┼"
private const val OUTLINE = "──"
private const val RIGHT_TOP_EDGE = "┐"
private const val TOP_EDGE = "┬"
private const val LEFT_TOP_EDGE = "┌"
private const val LEFT_BOTTOM_EDGE = "└"
private const val RIGHT_BOTTOM_EDGE = "┘"
private const val BOTTOM_EDGE = "┴"
private const val LEFT_EDGE = "├"
private const val RIGHT_EDGE = "┤"
private const val BLACK_STONE = "○"
private const val WHITE_STONE = "●"
private const val EMPTY_CHAR = ' '
private const val EMPTY_STRING = " "
private const val OMOK_START_MESSAGE = "오목 게임을 시작합니다."
private const val FORBIDDEN_MESSAGE = "놓을 수 없는 자리입니다. 빈 자리에 놓아주세요. (금수-렌주룰)"
private const val WINNER_MESSAGE = "Game-over 💫우승자💫는 %s."

private const val BOARD_SIZE = 15

class OutputView : GameEventListener {
    override fun onGameStart() {
        printStartMessage()
        printBoard(
            omok.board.gameBoard,
            omok.board.findForbiddenPositions(
                omok.currentStone,
            ),
        )
    }

    override fun printBoard(
        board: Array<Array<Stone>>,
        forbiddenPositions: List<Position>,
    ) {
        val boardForDisplay = initializeBoard()
        for (row in board.indices) {
            print("${row + 1}".padStart(2, EMPTY_CHAR) + EMPTY_STRING)
            for (col in board[row].indices) {
                val displayChar =
                    when {
                        Position(CoordsNumber(col), CoordsNumber(row)) in forbiddenPositions -> X
                        board[col][row] == Stone.WHITE -> WHITE_STONE
                        board[col][row] == Stone.BLACK -> BLACK_STONE
                        else -> boardForDisplay[row][col]
                    }
                print(displayChar)
                if (col < board[row].lastIndex) print(OUTLINE)
            }
            println()
        }
        println(COLUMN_STRING)
    }

    override fun onGameEnd(winner: Stone) {
        showWinner(winner)
    }

    private fun printStartMessage() {
        println(OMOK_START_MESSAGE)
    }

    fun printForbiddenMoveMessage() {
        println(FORBIDDEN_MESSAGE)
    }

    fun showWinner(currentStone: Stone) {
        println(WINNER_MESSAGE.format(currentStone))
    }

    private fun initializeBoard(): Array<Array<String>> {
        return Array(BOARD_SIZE) { row ->
            Array(BOARD_SIZE) { col ->
                when {
                    row == 0 -> topEdge(col)
                    row == 14 -> bottomEdge(col)
                    col == 0 -> leftEdge(row)
                    col == 14 -> rightEdge(row)
                    else -> EMPTY
                }
            }
        }
    }

    private fun topEdge(col: Int) =
        when (col) {
            0 -> LEFT_TOP_EDGE
            14 -> RIGHT_TOP_EDGE
            else -> TOP_EDGE
        }

    private fun bottomEdge(col: Int) =
        when (col) {
            0 -> LEFT_BOTTOM_EDGE
            14 -> RIGHT_BOTTOM_EDGE
            else -> BOTTOM_EDGE
        }

    private fun leftEdge(row: Int) = if (row in 1..13) LEFT_EDGE else EMPTY

    private fun rightEdge(row: Int) = if (row in 1..13) RIGHT_EDGE else EMPTY
}
