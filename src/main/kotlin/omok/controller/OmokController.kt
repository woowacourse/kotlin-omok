package omok.controller

import omok.model.Board
import omok.model.BoardState
import omok.model.Intersection
import omok.model.IntersectionState
import omok.view.InputView
import omok.view.OutputView
import rule.BlackRenjuRule
import rule.WhiteRenjuRule
import rule.wrapper.point.Point

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    private val blackRenjuRule = BlackRenjuRule(15, 15)
    private val whiteRenjuRule = WhiteRenjuRule(15, 15)

    fun run() {
        outputView.printOmokStart()
        val board = Board()
        outputView.printBoard(board)
        val point: Point = inputView.readInitialTurn()
        board.place(Intersection(point, IntersectionState.BLACK), blackRenjuRule)
        outputView.printBoard(board)
        retryOnError { processTurn(board) }
    }

    private tailrec fun processTurn(board: Board) {
        val point: Point = inputView.readTurn(board.lastStone)
        val stone: IntersectionState = board.lastStone.state.reverse()
        val intersection = Intersection(point, stone)
        val boardState =
            if (stone == IntersectionState.BLACK) {
                board.place(intersection, blackRenjuRule)
            } else {
                board.place(intersection, whiteRenjuRule)
            }

        outputView.printBoard(board)
        if (boardState == BoardState.PLAYING) {
            processTurn(board)
        }
    }

    private fun <T> retryOnError(function: () -> T): T {
        return runCatching { function() }.getOrElse { error ->
            println(error.message)
            retryOnError(function)
        }
    }
}
