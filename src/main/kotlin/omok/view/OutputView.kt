package omok.view

import omok.model.Color
import omok.model.Game
import omok.model.GameState
import rule.wrapper.point.Point

class OutputView {
    fun printOmokStart() {
        println(MESSAGE_OMOK_START)
        println()
    }

    fun printBoard(game: Game) {
        println(modifyBoard(game.blackPlayer.points.points, game.whitePlayer.points.points))
    }

    fun printWinner(gameState: GameState) {
        when (gameState) {
            GameState.WHITE_OMOK -> println(MESSAGE_OMOK_WINNER.format(WHITE_PLAYER))
            GameState.BLACK_OMOK -> println(MESSAGE_OMOK_WINNER.format(BLACK_PLAYER))
            GameState.PLAYING -> throw IllegalStateException()
        }
    }

    private fun modifyBoard(
        blackPoints: List<Point>,
        whitePoints: List<Point>,
    ): String {
        val board: String = makeInitialBoard(BOARD_SIZE, BOARD_SIZE)
        val lines = board.lines().toMutableList()
        whitePoints.forEach { (row, col) ->
            lines[BOARD_SIZE - row] = modifyLine(col, lines[BOARD_SIZE - row], Color.WHITE)
        }
        blackPoints.forEach { (row, col) ->
            lines[BOARD_SIZE - row] = modifyLine(col, lines[BOARD_SIZE - row], Color.BLACK)
        }

        return lines.joinToString("\n")
    }

    private fun modifyLine(
        col: Int,
        line: String,
        color: Color,
    ): String {
        val x = col - 1
        val sb = StringBuilder(line)
        sb[x + ROW_NUMBER_OFFSET_SIZE + x * COLUMN_NUMBER_OFFSET_SIZE] =
            when (color) {
                Color.BLACK -> BLACK_STONE
                Color.WHITE -> WHITE_STONE
            }
        return sb.toString()
    }

    private fun makeInitialBoard(
        rows: Int,
        cols: Int,
    ): String {
        val board = StringBuilder()

        board.append(FORMAT_ROW_NUMBER.format(rows))
        board.append(BOARD_TOP_LEFT)
        for (col in 2..<cols) {
            board.append(BOARD_TOP_MIDDLE)
        }
        board.append(BOARD_TOP_RIGHT)

        for (row in rows - 1 downTo 2) {
            board.append(FORMAT_ROW_NUMBER.format(row))
            board.append(BOARD_CENTER_LEFT)
            for (col in 2..<cols) {
                board.append(BOARD_CENTER_MIDDLE)
            }
            board.append(BOARD_CENTER_RIGHT)
        }

        board.append(FORMAT_ROW_NUMBER.format(1))
        board.append(BOARD_BOTTOM_LEFT)
        for (col in 2..<cols) {
            board.append(BOARD_BOTTOM_MIDDLE)
        }
        board.append(BOARD_BOTTOM_RIGHT)

        val bottomLine = StringBuilder("  ")
        bottomLine.append(" ".repeat(rows.toString().length))
        for (alphabet in 1..cols) {
            bottomLine.append("${(alphabet + 64).toChar()}  ")
        }
        board.append(bottomLine)

        return board.toString()
    }

    companion object {
        private const val MESSAGE_OMOK_START = "오목 게임을 시작합니다."
        private const val MESSAGE_OMOK_WINNER = "%s이 승리했습니다!"
        private const val BLACK_PLAYER = "흑"
        private const val WHITE_PLAYER = "백"

        private const val BOARD_SIZE = 15
        private const val ROW_NUMBER_OFFSET_SIZE = 4
        private const val COLUMN_NUMBER_OFFSET_SIZE = 2
        private const val FORMAT_ROW_NUMBER = "%3d "

        private const val BLACK_STONE = '●'
        private const val WHITE_STONE = '○'

        private const val BOARD_TOP_LEFT = "┌─"
        private const val BOARD_TOP_MIDDLE = "─┬─"
        private const val BOARD_TOP_RIGHT = "─┐\n"

        private const val BOARD_CENTER_LEFT = "├─"
        private const val BOARD_CENTER_MIDDLE = "─┼─"
        private const val BOARD_CENTER_RIGHT = "─┤\n"

        private const val BOARD_BOTTOM_LEFT = "└─"
        private const val BOARD_BOTTOM_MIDDLE = "─┴─"
        private const val BOARD_BOTTOM_RIGHT = "─┘\n"
    }
}
