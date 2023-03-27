package view

import domain.Board
import domain.Board.Companion.MIN_VIEW_X
import domain.Board.Companion.MIN_VIEW_Y
import domain.stone.Point

object OutputView {

    fun printBoard(board: Board) {
        return println(BoardView(board))
    }

    fun printLastPoint(point: Point){
        println(" (마지막 돌의 위치: ${(point.x + MIN_VIEW_X.code).toChar() + (point.y + MIN_VIEW_Y).toString()})")
    }
}
