package woowacourse.omok.view

import woowacourse.omok.model.Color
import woowacourse.omok.model.Color.BLACK
import woowacourse.omok.model.Color.WHITE
import woowacourse.omok.model.GameResult
import woowacourse.omok.model.Position
import woowacourse.omok.model.Stone

class ConsoleOutputView {
    fun showGameStartMessage() {
        println(MESSAGE_GAME_START)
    }

    fun showCurrentBoard(board: List<List<Color?>>) {
        println()
        for (i in 1..<board.size) {
            print(ROW_INDICATOR.format(BOARD_SIZE - i))
            showSingleRow(board, i)
            println(DIVIDER_ROW)
        }
        println(COLUMN_INDICATOR)
    }

    fun showCurrentTurn(lastTurn: Stone?) {
        lastTurn?.let {
            val currentColor = Color.getReversedColor(it.color)
            println(MESSAGE_PLAYERS_TURN.format(getCurrentColor(currentColor)))
            showLastPosition(lastTurn.position)
        } ?: println(MESSAGE_PLAYERS_TURN.format(COLOR_BLACK))
    }

    fun showGameResult(gameResult: GameResult) {
        when (gameResult) {
            GameResult.DRAW -> println(MESSAGE_DRAW)
            else -> println(MESSAGE_WINNER.format(gameResult.label))
        }
    }

    private fun showSingleRow(
        board: List<List<Color?>>,
        i: Int,
    ) {
        for (j in 1..<board[i].size) {
            print(getSingleCell(board[i][j]))
            if (j < board[i].size - 1) print(DIVIDER_ROW)
        }
    }

    private fun getSingleCell(stone: Color?) =
        when (stone) {
            null -> SIGNATURE_BLANK
            BLACK -> SIGNATURE_BLACK
            WHITE -> SIGNATURE_WHITE
        }

    private fun getCurrentColor(currentColor: Color): String =
        when (currentColor) {
            Color.BLACK -> COLOR_BLACK
            Color.WHITE -> COLOR_WHITE
        }

    private fun showLastPosition(position: Position) {
        println(MESSAGE_LAST_PLACE.format(position.verticalCoordinate.name, position.horizontalCoordinate.index))
    }

    companion object {
        private const val COLUMN_INDICATOR = "   A B C D E F G H I J K L M N O\n"
        private const val ROW_INDICATOR = "%2d─"
        private const val SIGNATURE_BLANK = "┼"
        private const val SIGNATURE_BLACK = "●"
        private const val SIGNATURE_WHITE = "○"
        private const val DIVIDER_ROW = "─"
        private const val BOARD_SIZE = 16
        private const val MESSAGE_GAME_START = "오목 게임을 시작합니다.\n"
        private const val MESSAGE_DRAW = "무승부 입니다"
        private const val MESSAGE_WINNER = "%s이 이겼습니다"
        private const val COLOR_BLACK = "흑"
        private const val COLOR_WHITE = "백"
        private const val MESSAGE_PLAYERS_TURN = "%s의 차례입니다."
        private const val MESSAGE_INPUT_POSITION = "위치를 입력하세요: "
        private const val MESSAGE_LAST_PLACE = "(마지막 돌의 위치: %s%d)"
    }
}
