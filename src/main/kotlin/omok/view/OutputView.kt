package omok.view

import omok.model.Game
import omok.model.GameState
import omok.model.Point
import omok.model.Stone
import omok.model.StoneColor

class OutputView {
    fun printOmokStart() {
        println(MESSAGE_OMOK_START)
        println()
    }

    fun printBoard(game: Game) {
        // println(modifyBoard(game.blackPlayer.stones.points, game.whitePlayer.stones.points))
        // TODO 출력 수정
    }

    fun printWinner(gameState: GameState) {
        when (gameState) {
            GameState.WHITE_OMOK -> println(MESSAGE_OMOK_WINNER.format(WHITE_PLAYER))
            GameState.BLACK_OMOK -> println(MESSAGE_OMOK_WINNER.format(BLACK_PLAYER))
            GameState.PLAYING -> throw IllegalStateException()
        }
    }

    private fun modifyBoard(
        blackStones: Set<Point>,
        whiteStones: Set<Point>,
    ): String {
        val board: String = makeInitialBoard(BOARD_SIZE, BOARD_SIZE)
        val lines = board.lines().toMutableList()
        whiteStones.forEach { (row, col) ->
            lines[BOARD_SIZE - row] = modifyLine(col, lines[BOARD_SIZE - row], StoneColor.WHITE)
        }
        blackStones.forEach { (row, col) ->
            lines[BOARD_SIZE - row] = modifyLine(col, lines[BOARD_SIZE - row], StoneColor.BLACK)
        }

        return lines.joinToString("\n")
    }

    private fun modifyLine(
        col: Int,
        line: String,
        color: StoneColor,
    ): String {
        val x = col - 1
        val sb = StringBuilder(line)
        sb[x + ROW_NUMBER_OFFSET_SIZE + x * COLUMN_NUMBER_OFFSET_SIZE] =
            when (color) {
                StoneColor.BLACK -> BLACK_STONE
                StoneColor.WHITE -> WHITE_STONE
            }
        return sb.toString()
    }

    private fun makeInitialBoard(
        rows: Int,
        cols: Int,
    ): String {
        val board = StringBuilder()

        board.append(FORMAT_ROW_NUMBER.format(rows))
        board.append("┌──")
        for (col in 2..<cols) {
            board.append("┬──")
        }
        board.append("┐\n")

        for (row in rows - 1 downTo 2) {
            board.append(FORMAT_ROW_NUMBER.format(row))
            board.append("├──")
            for (col in 2..<cols) {
                board.append("┼──")
            }
            board.append("┤\n")
        }

        board.append(FORMAT_ROW_NUMBER.format(1))
        board.append("└──")
        for (col in 2..<cols) {
            board.append("┴──")
        }
        board.append("┘\n")

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
    }
}
