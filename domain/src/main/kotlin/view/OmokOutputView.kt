package view

import domain.game.Omok.Companion.OMOK_BOARD_SIZE
import domain.player.Player
import domain.point.Point
import domain.result.TurnResult
import domain.stone.StoneColor
import view.mapper.toPresentation

class OmokOutputView {
    fun onStartGame() {
        println(START_MESSAGE)
        println(OMOK_BOARD)
    }

    fun onEndGame(result: TurnResult) {
        println(GAME_END_MESSAGE)
        if (result is TurnResult.Foul) {
            println("금수를 두었습니다.")
            println(WINNER_MESSAGE.format(result.winColor.toPresentation().text))
            return
        }
        if (result is TurnResult.Win) println(WINNER_MESSAGE.format(result.winColor.toPresentation().text))
    }

    fun onStartTurn(stoneColor: StoneColor, point: Point?) {
        print(TURN_MESSAGE.format(stoneColor.toPresentation().text))
        if (point != null) {
            val viewPosition = point.toPresentation()
            print(LAST_STONE_POSITION_MESSAGE.format(viewPosition))
        }
        println()
    }

    fun onEndTurn(result: TurnResult) {
        if (result is TurnResult.Playing && result.isExistPoint) println(ALREADY_EXIST_STONE)
        val board = OMOK_BOARD.toMutableList()
        val players = result.players
        players.toList().forEach { player ->
            player.getAllPoints().getAll().forEach { drawPoint(player, board, it) }
        }
        println(board.joinToString(separator = ""))
    }

    private fun drawPoint(player: Player, board: MutableList<Char>, it: Point) {
        when (player.getColor()) {
            StoneColor.BLACK -> board[calculateIndex(it, OMOK_BOARD_SIZE)] = BLACK_STONE
            StoneColor.WHITE -> board[calculateIndex(it, OMOK_BOARD_SIZE)] = WHITE_STONE
        }
    }

    private fun calculateIndex(point: Point, size: Int): Int = (1 + 3 * point.col) + (size - point.row) * (3 + 3 * size)

    companion object {
        private const val START_MESSAGE = "오목 게임을 시작합니다."
        private const val TURN_MESSAGE = "%s의 차례입니다. "
        private const val LAST_STONE_POSITION_MESSAGE = "(마지막 돌의 위치: %s)"
        private const val ALREADY_EXIST_STONE = "이미 돌이 있습니다."
        private const val GAME_END_MESSAGE = "게임이 종료되었습니다."
        private const val WINNER_MESSAGE = "%s의 승리입니다."
        private const val BLACK_STONE = '●'
        private const val WHITE_STONE = '○'
        private const val BOTTOM_NUMBER = 1

        private fun createOmokBoard(size: Int): String {
            val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            val topHorizontal = "┌──" + "┬──".repeat(size - 3) + "┬──┐"
            val bottomHorizontal = "└──" + "┴──".repeat(size - 3) + "┴──┘"
            val middleHorizontal = "├──" + "┼──".repeat(size - 3) + "┼──┤"
            val board = StringBuilder()
            for (i in size downTo 1) {
                board.append(String.format("%3d ", i))
                when {
                    i == size -> board.append(topHorizontal) // top
                    i == BOTTOM_NUMBER -> board.append(bottomHorizontal) // middle
                    i != BOTTOM_NUMBER -> board.append(middleHorizontal) // bottom
                }
                board.append("\n")
            }
            board.append("    ")
            for (j in 0 until size) { board.append(letters[j] + "  ") }
            return board.toString()
        }
        private val OMOK_BOARD: String = createOmokBoard(OMOK_BOARD_SIZE)
    }
}
