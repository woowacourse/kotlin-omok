package omok.controller

import omok.model.board.Board
import omok.model.board.BoardSize
import omok.model.rule.BudoolRenjuRuleAdapter
import omok.model.rule.OmokReferee
import omok.model.stone.position.Position
import omok.view.InputView
import omok.view.OutputView

class OmokControl(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val boardSize: BoardSize,
) {
    private val omokReferee = OmokReferee(BudoolRenjuRuleAdapter(boardSize))

    fun run() {
        val board = Board(boardSize)
        turn(board)
    }

    private tailrec fun turn(board: Board) {
        outputView.printBoard(board.stonesMap)
        outputView.printNextTurn(board)

        val nextBoard = stoneAddedBoard(board)
        if (omokReferee.isLastStoneOmok(nextBoard)) {
            outputView.printBoard(nextBoard.stonesMap)
            outputView.printOmok(nextBoard.lastStone)
            return
        } else {
            turn(nextBoard)
        }
    }

    private tailrec fun stoneAddedBoard(board: Board): Board {
        runCatching {
            val inputCoordinateText = inputView.inputStone()
            val newBoard = board.nextStonePlacedBoard(Position(inputCoordinateText))
            omokReferee.lastStoneFoulCheck(newBoard)
            return newBoard
        }.getOrElse { exception ->
            outputView.printException(exception.message)
            return stoneAddedBoard(board)
        }
    }
}
