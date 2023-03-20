package view

import domain.board.Board
import domain.stone.Color

object OutputView {

    private const val GAME_START_MESSAGE = "오목 게임을 시작합니다."
    private const val WINNER = "승리자: %s"

    fun printGameStartMessage() {
        println(GAME_START_MESSAGE)
    }

    fun printOmokBoard(currentBoard: Board) {
        println(BoardMaker.make(currentBoard))
    }

    fun printWinner(color: Color?) {
        println(WINNER.format(color))
    }

    fun printExceptionMessage(throwable: Throwable) {
        println(throwable.message)
    }
}
