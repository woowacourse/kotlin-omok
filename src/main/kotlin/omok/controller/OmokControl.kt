package omok.controller

import omok.model.board.Board
import omok.model.board.Board.Companion.initBoard
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import omok.view.InputView
import omok.view.OutputView

class OmokControl(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        val board = initBoard()
        turn(board)
    }

    private fun turn(board: Board) {
        outputView.printBoard(board.stonesMap)
        outputView.printNextTurn(board)

        val nextBoard = stoneAddedBoard(board)
        if (nextBoard.isLastStoneOmok) {
            outputView.printBoard(nextBoard.stonesMap)
            outputView.printOmok(nextBoard.lastStone)
        } else {
            turn(nextBoard)
        }
    }

    private fun stoneAddedBoard(board: Board): Board {
        val result =
            runCatching {
                val input = inputView.inputStone()
                board.placeStone(Position(Row(input.first), Col(input.second)))
            }.getOrElse { exception ->
                outputView.printException(exception.message)
                return stoneAddedBoard(board)
            }

        return result
    }
}
