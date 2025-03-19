package omok.controller

import omok.model.Board
import omok.model.Board.Companion.initBoard
import omok.model.Col
import omok.model.Position
import omok.model.Row
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

        val newBoard = retryInput(board)
        if (newBoard.isLastStoneOmok()) {
            outputView.printBoard(newBoard.stonesMap)
            outputView.printOmok(newBoard.lastStone)
        } else {
            turn(newBoard)
        }
    }

    private fun retryInput(board: Board): Board {
        val result =
            runCatching {
                val input = inputView.inputStone()
                val parsedPosition = parseUserInput(input)
                board.placeStone(parsedPosition!!)
            }.getOrElse { exception ->
                println("$exception")
                return retryInput(board)
            }

        return result
    }

    private fun parseUserInput(input: String): Position? {
        val columnChar = input[0].uppercaseChar()
        val rowNumber = input.substring(1).toIntOrNull()

        if (columnChar !in 'A'..'O' || rowNumber == null || rowNumber !in 1..15) {
            return null
        }

        val col = Col(columnChar - 'A')
        val row = Row(rowNumber - 1)

        return Position(row, col)
    }
}
