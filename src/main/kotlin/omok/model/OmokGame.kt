package omok.model

import omok.model.StoneColor.Companion.next
import omok.model.board.Board
import omok.model.board.BoardSize
import omok.model.board.PlaceStoneResult
import omok.model.board.Point
import omok.model.rule.count.FiveInRowRule
import omok.model.rule.count.OmokCountRule
import omok.view.OmokInputView
import omok.view.OmokOutputView

class OmokGame(
    private val inputView: OmokInputView,
    private val outputView: OmokOutputView,
) {
    private var previousPoint: Point? = null
    private var currentStoneColor: StoneColor = StoneColor.BLACK

    fun play(boardSize: BoardSize) {
        val board = Board(boardSize)
        outputView.printStartMessage()
        outputView.printBoardStatus(board)

        playTurn(board)
    }

    private fun playTurn(board: Board) {
        placeStone(board)

        if (checkOmok(FiveInRowRule, board)) {
            showWinColor()
        }
    }

    private fun checkOmok(
        omokCountRule: OmokCountRule,
        board: Board,
    ): Boolean {
        val point = previousPoint ?: return false
        return board.isOmok(point, omokCountRule)
    }

    private fun placeStone(board: Board) =
        retryOnException {
            val pos = getNextPoint()
            when (val result = board.placeStone(pos, currentStoneColor)) {
                is PlaceStoneResult.Success -> {
                    handlePlaceSuccess(result, board)
                    return@retryOnException
                }

                is PlaceStoneResult.AlreadyPlaced -> throw IllegalArgumentException(ALREADY_PLACED_ERROR_MESSAGE)
                is PlaceStoneResult.Closed -> throw IllegalArgumentException(CLOSED_ERROR_MESSAGE)
            }
        }

    private fun handlePlaceSuccess(
        result: PlaceStoneResult.Success,
        board: Board,
    ) {
        previousPoint = result.point
        currentStoneColor = currentStoneColor.next()
        outputView.printBoardStatus(board)
    }

    private fun showWinColor() {
        outputView.printWinColor(previousPoint!!)
    }

    private fun getNextPoint(): Point =
        retryOnException {
            outputView.printCurrentTurn(previousPoint)
            val nextPoint = inputView.readPosition()

            Point(nextPoint.first, nextPoint.second)
        }

    private fun <T> retryOnException(action: () -> T) =
        omok.utils.retryOnException(
            action = action,
            onFailure = { outputView.printErrorMessage(it.message.toString()) },
        )

    companion object {
        private const val ALREADY_PLACED_ERROR_MESSAGE = "중복되는 칸에 돌을 둘 수 없습니다."
        private const val CLOSED_ERROR_MESSAGE = "둘 수 없는 자리입니다."
    }
}
