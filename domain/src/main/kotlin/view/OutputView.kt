package view

import domain.Board
import domain.MIN_VIEW_X
import domain.MIN_VIEW_Y

object OutputView {

    fun printBoard(board: Board) {
        return println(BoardView(board))
    }

    fun printLastPoint(point: Pair<Int, Int>){
        val (x,y) = point
        println(" (마지막 돌의 위치: ${(x + MIN_VIEW_X.code).toChar() + (y + MIN_VIEW_Y).toString()})")
    }
}
