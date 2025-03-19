package omok.controller

import omok.model.Board
import omok.model.BoardState
import omok.model.Intersection
import omok.model.IntersectionState
import omok.model.Position
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    val inputView: InputView,
    val outputView: OutputView,
) {
    fun run() {
        outputView.printOmokStart()
        val board = Board()
        outputView.printBoard(board)
        val position: Position = inputView.readInitialTurn()
        board.place(Intersection(position, IntersectionState.BLACK))
        outputView.printBoard(board)
        processTurn(board)
    }

    private tailrec fun processTurn(board: Board) {
        val position: Position = inputView.readTurn(board.lastStone)
        val stone: IntersectionState = board.lastStone.state.reverse()
        val intersection = Intersection(position, stone)
        board.place(intersection)
        outputView.printBoard(board)
        val boardState: BoardState = board.check(intersection)
        if (boardState == BoardState.PLAYING) {
            processTurn(board)
        }
    }
}
