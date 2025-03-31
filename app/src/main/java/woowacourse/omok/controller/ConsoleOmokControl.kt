package woowacourse.omok.controller

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.board.PositionStatus
import woowacourse.omok.model.rule.BudoolRenjuRuleAdapter
import woowacourse.omok.model.rule.OmokReferee
import woowacourse.omok.model.rule.RenjuFoul
import woowacourse.omok.model.stone.position.Col
import woowacourse.omok.model.stone.position.Position
import woowacourse.omok.model.stone.position.Row
import woowacourse.omok.view.consoleView.InputView
import woowacourse.omok.view.consoleView.OutputView

class ConsoleOmokControl(
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
        val nextPosition = readValidPosition(board)
        val newBoard = board.nextStonePlacedBoard(nextPosition)
        val foul = omokReferee.lastStoneFoul(newBoard)

        if (foul == RenjuFoul.SAFE) {
            return newBoard
        }
        outputView.printFoul(foul)
        return stoneAddedBoard(board)
    }

    private tailrec fun readValidPosition(board: Board): Position {
        val (row, col) = readRowCol()
        val validPosition = Position(row, col)

        val positionState = board.positionStatus(validPosition)

        if (positionState == PositionStatus.EMPTY) {
            return validPosition
        }
        outputView.printPositionStatus(positionState)
        return readValidPosition(board)
    }

    private tailrec fun readRowCol(): Pair<Row, Col> {
        val coordinateText = inputView.readCoordinateText()
        val colAlphabet = coordinateText[0].uppercaseChar()
        val rowNumberText = (coordinateText.substring(1))
        val isColValid = isCanColAlphabetParse(colAlphabet)
        val isRowValid = isCanRowNumberParse(rowNumberText)
        when {
            !isColValid || !isRowValid -> inputView.inputExceptionAlert(ERROR_COORDINATE_STRING)
            else -> return Row(rowNumberText.toInt() - ROW_OFFSET) to Col(colAlphabet - MIN_COL_CHAR)
        }
        return readRowCol()
    }

    private fun isCanColAlphabetParse(colAlphabet: Char): Boolean = colAlphabet in MIN_COL_CHAR..MAX_COL_CHAR

    private fun isCanRowNumberParse(rowNumberText: String): Boolean = rowNumberText.toIntOrNull() != null

    companion object {
        private const val MIN_COL_CHAR = 'A'
        private const val MAX_COL_CHAR = 'Z'
        private const val ERROR_COORDINATE_STRING = "유효하지 않은 좌표값 입력입니다"

        private const val ROW_OFFSET = 1
    }
}
