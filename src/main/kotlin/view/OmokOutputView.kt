package view

import domain.player.Player
import domain.player.Players
import domain.point.Point
import domain.stone.StoneColor
import view.mapper.toPresentation

class OmokOutputView : OutputView {
    override fun onStartGame() {
        println(START_MESSAGE)
        println(EMPTY_BOARD)
    }

    override fun onEndGame(stoneColor: StoneColor) {
        println(GAME_END_MESSAGE)
        println(WINNER_MESSAGE.format(stoneColor.toPresentation().text))
    }

    override fun onStartTurn(stoneColor: StoneColor, point: Point?) {
        print(TURN_MESSAGE.format(stoneColor.toPresentation().text))
        if (point != null) {
            val viewPosition = point.toPresentation()
            print(LAST_STONE_POSITION_MESSAGE.format(viewPosition))
        }
        println()
    }

    override fun onEndTurn(players: Players) {
        val board = EMPTY_BOARD.toMutableList()
        val board2 = EMPTY_BOARD.toMutableList()
        players.toList().forEach { player ->
            player.getAllPoints().getAll().forEach { drawPoint(player, board, it) }
        }
        println(board.joinToString(separator = ""))
    }

    private fun drawPoint(player: Player, board: MutableList<Char>, it: Point) {
        when (player.getStoneColor()) {
            StoneColor.BLACK -> board[calculateIndex(it)] = BLACK_STONE
            StoneColor.WHITE -> board[calculateIndex(it)] = WHITE_STONE
        }
    }

    private fun calculateIndex(point: Point): Int = 721 + 3 * point.col - 48 * point.row

    private fun calculateIndex2(point: Point): Int = 721 + 3 * point.col - 48 * point.row

    companion object {
        private const val START_MESSAGE = "오목 게임을 시작합니다."
        private const val TURN_MESSAGE = "%s의 차례입니다. "
        private const val LAST_STONE_POSITION_MESSAGE = "(마지막 돌의 위치: %s)"
        private const val GAME_END_MESSAGE = "게임이 종료되었습니다."
        private const val WINNER_MESSAGE = "%s의 승리입니다."
        private val EMPTY_BOARD: String = """
            | 15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
            | 14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            | 13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            | 12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            | 11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            | 10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            |  9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            |  8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            |  7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            |  6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            |  5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            |  4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            |  3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            |  2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            |  1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
            |    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
        """.trimMargin()
        private const val BLACK_STONE = '●'
        private const val WHITE_STONE = '○'
    }
}
