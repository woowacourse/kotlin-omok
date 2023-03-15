package view

import domain.player.Players
import domain.position.Position
import domain.stone.StoneColor
import listener.OmokStartEndEventListener
import view.model.BoardModel
import view.model.ColorModel

class OutputView : OmokStartEndEventListener {
    override fun onStartGame() {
        printStart()
    }

    override fun onEndGame(stoneColor: StoneColor) {
        printWinner(stoneColor)
    }

    override fun onStartTurn(stoneColor: StoneColor, position: Position) {
        printTurn(stoneColor, position)
    }

    override fun onEndTurn(players: Players) {
        printOmokBoard(players)
    }

    private fun printStart() {
        println(START_MESSAGE)
        println(EMPTY_BOARD)
        println(TURN_MESSAGE.format(ColorModel.getString(StoneColor.BLACK)))
    }

    private fun printOmokBoard(players: Players) {
        val board = EMPTY_BOARD.toMutableList()

        players.getBlackPlayer().getPositions().forEach {
            board[calculateIndex(it)] = BLACK_STONE
        }
        players.getWhitePlayer().getPositions().forEach {
            board[calculateIndex(it)] = WHITE_STONE
        }
        println(board.joinToString(separator = ""))
    }

    private fun calculateIndex(position: Position): Int = 721 + 3 * position.col - 48 * position.row

    private fun printTurn(stoneColor: StoneColor, position: Position) {
        print(TURN_MESSAGE.format(ColorModel.getString(stoneColor)))
        println(LAST_STONE_POSITION_MESSAGE.format(BoardModel.getString(position)))
    }

    private fun printWinner(stoneColor: StoneColor) {
        println(GAME_END_MESSAGE)
        println(WINNER_MESSAGE.format(ColorModel.getString(stoneColor)))
    }

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
