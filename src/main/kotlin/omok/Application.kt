import omok.model.Board
import omok.model.BoardColumn
import omok.model.Stone
import omok.view.InputView
import omok.view.OutputVIew

fun main() {
    var input = ""
    val board = Array(15) { Array(15) { 0 } }
    OutputVIew.printStartMessage()
    val rule = Board(board)
    var currentStone = Stone.BLACK
    board[0][1] = 1
    board[8][5] = 1
    board[7][5] = 1
    board[6][7] = 1
    board[6][8] = 1
    var forbiddenPositions = rule.checkBoard(currentStone.value)
    while (rule.isRunning()) {
        forbiddenPositions = rule.checkBoard(currentStone.value)
        OutputVIew.printBoard(board, forbiddenPositions)
        input = InputView.readPlayerInput(currentStone, input)
        val columnLetter = (input[0].uppercase())[0]
        val columnNumber = input.substring(1).toInt() - 1
        val rowNumber = BoardColumn.fromLetter(columnLetter)?.column!! - 1
        if (board[rowNumber][columnNumber] == Board.EMPTY) {
            rule.setStone(columnNumber, rowNumber, currentStone.value)
            currentStone = changeColor(currentStone)
        } else {
            InputView.printWorngPosition()
        }
    }
    OutputVIew.printBoard(board, forbiddenPositions)
}

private fun changeColor(currentStone: Stone): Stone {
    var currentStone1 = currentStone
    if (currentStone1 == Stone.BLACK) {
        currentStone1 = Stone.WHITE
    } else {
        currentStone1 = Stone.BLACK
    }
    return currentStone1
}
