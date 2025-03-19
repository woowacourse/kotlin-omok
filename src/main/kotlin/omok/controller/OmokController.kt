package omok.controller

import omok.model.Board
import omok.model.BoardState
import omok.model.Intersection
import omok.model.IntersectionState
import omok.view.InputView
import omok.view.OutputView
import rule.wrapper.point.Point

class OmokController(
    val inputView: InputView,
    val outputView: OutputView,
) {
    fun run() {
        outputView.printOmokStart()
        val board = Board()
        outputView.printBoard(board)
        val point: Point = inputView.readInitialTurn()
        board.place(Intersection(point, IntersectionState.BLACK))
        outputView.printBoard(board)
        retryOnError { processTurn(board) }
    }

    private tailrec fun processTurn(board: Board) {
        val point: Point = inputView.readTurn(board.lastStone)
        val stone: IntersectionState = board.lastStone.state.reverse()
        val intersection = Intersection(point, stone)
        board.place(intersection)

        outputView.printBoard(board)
        val boardState: BoardState = board.check(intersection)
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
