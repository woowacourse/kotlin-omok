package woowacourse.omok.view

import woowacourse.omok.model.Board.Companion.BOARD_SIZE
import woowacourse.omok.model.Color
import woowacourse.omok.model.Coordinate
import woowacourse.omok.model.StoneState
import woowacourse.omok.model.Stones

object OutputView {
    private const val MESSAGE_START = "오목 게임을 시작합니다.\n"
    private const val MESSAGE_TURN = "%s의 차례입니다. "
    private const val MESSAGE_LAST_COORDINATE = "(마지막 돌의 위치: %s)"
    private const val MESSAGE_STONE_OUTSIDE_THE_BOARD = "돌의 위치가 바둑판을 벗어났습니다."
    private const val MESSAGE_OCCUPIED_STONE = "이미 돌이 착수된 위치입니다."
    private const val MESSAGE_FORBIDDEN_STONE = "해당 위치는 금수입니다."
    private const val MESSAGE_PLAYER_COLOR = "%s 플레이어가 이겼습니다 !"
    private const val PLAYER_COLOR_BLACK = "흑"
    private const val PLAYER_COLOR_WHITE = "백"
    private const val BLACK_STONE = '●'
    private const val WHITE_STONE = '○'
    private const val SINGLE_DIGIT: Int = 9
    private var boardLines = mutableListOf<String>()

    fun printStart(stones: Stones) {
        initializeBoard()
        println(MESSAGE_START)
        printBoard(stones)
    }

    private fun initializeBoard() {
        for (row in BOARD_SIZE downTo 1) {
            val line = if (row > SINGLE_DIGIT) " $row ├──" else "  $row ├──"
            boardLines.add(line + "┼──".repeat(BOARD_SIZE - 2) + "┤")
        }
        boardLines[0] = boardLines[0].replace('├', '┌').replace('┼', '┬').replace('┤', '┐')
        boardLines[BOARD_SIZE - 1] = boardLines[BOARD_SIZE - 1].replace('├', '└').replace('┼', '┴').replace('┤', '┘')
        boardLines.add("    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O\n")
    }

    fun printTurnName(color: Color) {
        print(MESSAGE_TURN.format(getPlayerColor(color)))
    }

    private fun getPlayerColor(color: Color): String {
        return when (color) {
            Color.BLACK -> PLAYER_COLOR_BLACK
            Color.WHITE -> PLAYER_COLOR_WHITE
        }
    }

    fun printBoard(stones: Stones) {
        val tempBoard = boardLines.toMutableList()
        stones.stones.forEach { stone ->
            val row = stone.coordinate.row.value - 1
            val col = (stone.coordinate.col.value) * 3 + 1
            val line = tempBoard[row]

            tempBoard[row] =
                line.substring(0, col) + getStoneColor(stone.color) + line.substring(col + 1)
        }

        tempBoard.forEach { println(it) }
    }

    private fun getStoneColor(color: Color): Char {
        return when (color) {
            Color.BLACK -> BLACK_STONE
            Color.WHITE -> WHITE_STONE
        }
    }

    fun printLastStone(coordinate: Coordinate?) {
        if (coordinate != null) {
            val col = coordinate.col.value
            val row = BOARD_SIZE - coordinate.row.value + 1
            val colCh = 'A' + col - 1
            print(MESSAGE_LAST_COORDINATE.format(colCh + "" + row))
        }
        println()
    }

    fun printErrorMessage(errorMessage: String) {
        println(errorMessage)
    }

    fun printForbiddenStone(stoneState: StoneState) {
        when (stoneState) {
            StoneState.OUTSIDE_THE_BOARD -> println(MESSAGE_STONE_OUTSIDE_THE_BOARD)
            StoneState.FORBIDDEN -> println(MESSAGE_FORBIDDEN_STONE)
            StoneState.OCCUPIED -> println(MESSAGE_OCCUPIED_STONE)
            StoneState.PLACED -> return
            StoneState.BEFORE_PLACED -> return
        }
    }

    fun printWinner(winnerColor: Color) {
        println(MESSAGE_PLAYER_COLOR.format(getPlayerColor(winnerColor)))
    }
}
