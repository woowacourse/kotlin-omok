package view

import domain.board.Board
import domain.stone.Color

object OutputView {

    private const val GAME_START_MESSAGE = "오목 게임을 시작합니다."
    private const val WINNER = "승리자: %s"
    private const val ERROR_GAME_END = "[ERROR] 비정상적으로 오목 게임이 종료됐습니다."

    fun printGameStartMessage() {
        println(GAME_START_MESSAGE)
    }

    fun printOmokBoard(currentBoard: Board) {
        println(BoardMaker.make(currentBoard))
    }

    fun printWinner(color: Color?) {
        color?.let {
            println(WINNER.format(it))
            return
        }
        println(ERROR_GAME_END)
    }
}
