package view

import domain.board.Board
import domain.stone.Color

object OutputView {

    private const val COL_UNIT = 3
    private const val END_COL = 15
    private const val INITIAL_ROW = 0
    private const val END_ROW = 14
    private const val GAME_START_MESSAGE = "오목 게임을 시작합니다."
    private const val WINNER = "승리자: %s"
    private const val ERROR_GAME_END = "[ERROR] 비정상적으로 오목 게임이 종료됐습니다."
    private const val BLACK_STONE = "●"
    private const val WHITE_STONE = "◎"
    private const val LEFT_TOP = "┌"
    private const val RIGHT_TOP = "┐"
    private const val CONNECTING_HORIZONTAL = "─"
    private const val CONNECTING_HORIZONTAL_VERTICAL = "┼"
    private const val LEFT_BOTTOM = "└"
    private const val RIGHT_BOTTOM = "┘"

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
