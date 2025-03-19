package omok.model

import omok.model.board.Board
import omok.model.board.PlaceStoneResult
import omok.model.board.Point
import omok.model.rule.FiveInRow
import omok.model.rule.Rule
import omok.model.stone.Position
import omok.model.stone.StoneColor
import omok.model.stone.StoneColor.Companion.next
import omok.view.OmokInputView
import omok.view.OmokOutputView

class OmokGame(
    private val inputView: OmokInputView,
    private val outputView: OmokOutputView,
) {
    private var previousPoint: Point? = null
    private var currentStoneColor: StoneColor = StoneColor.BLACK

    fun play() {
        val board = Board()
        outputView.printStartMessage()
        outputView.printBoardStatus(board)

        playTurn(board)
    }

    private fun playTurn(board: Board) {
        val rule: Rule = FiveInRow()

        while (judgeRule(rule, board)) {
            val pos = getNextPoint()
            placeStone(board, pos)
        }

        showWinColor()
    }

    private fun placeStone(
        board: Board,
        pos: Position,
    ) {
        when (val result = board.placeStone(pos, currentStoneColor)) {
            is PlaceStoneResult.Success -> {
                previousPoint = result.point
                currentStoneColor = currentStoneColor.next()
                outputView.printBoardStatus(board)
            }

            is PlaceStoneResult.AlreadyPlaced -> {
                outputView.printErrorMessage(ALREADY_PLACED_ERROR_MESSAGE)
            }
            is PlaceStoneResult.Closed -> {
                outputView.printErrorMessage(CLOSED_ERROR_MESSAGE)
            }
        }
    }

    private fun judgeRule(
        rule: Rule,
        board: Board,
    ): Boolean {
        if (previousPoint == null) return true
        return !rule.calculate(board, previousPoint!!)
    }

    private fun showWinColor() {
        outputView.printWinColor(previousPoint!!)
    }

    private fun getNextPoint(): Position {
        outputView.printCurrentTurn(previousPoint)
        val nextPosition = inputView.readPosition()
        return Position(nextPosition.first, nextPosition.second)
    }

    companion object {
        private const val ALREADY_PLACED_ERROR_MESSAGE = "중복되는 칸에 돌을 둘 수 없습니다."
        private const val CLOSED_ERROR_MESSAGE = "둘 수 없는 자리입니다."
    }
}
