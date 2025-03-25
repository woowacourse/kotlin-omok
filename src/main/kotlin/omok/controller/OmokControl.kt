package omok.controller

import omok.model.board.Board
import omok.model.board.BoardSize
import omok.model.rule.BudoolRenjuRuleAdapter
import omok.model.rule.OmokReferee
import omok.model.rule.RenjuFoul
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
        if (omokReferee.isOmok(nextBoard)) {
            outputView.printBoard(nextBoard.stonesMap)
            outputView.printOmok(nextBoard.lastStone)
            return
        } else {
            turn(nextBoard)
        }
    }

    private tailrec fun stoneAddedBoard(board: Board): Board {
        val inputCoordinateText = inputView.inputStone()
        val newBoard = board.nextStonePlacedBoard(Position(inputCoordinateText))
        val foul = omokReferee.lastStoneFoul(newBoard)

        if (foul == RenjuFoul.SAFE) {
            return newBoard
        }
        outputView.printFoul(foul)
        return stoneAddedBoard(board)
    }
}
