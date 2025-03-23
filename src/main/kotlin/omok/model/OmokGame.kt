package omok.model

import omok.controller.OmokGameListener
import omok.model.board.Board
import omok.model.board.PlaceStoneResult
import omok.model.board.Point
import omok.model.board.PointState
import omok.model.rule.OmokRuleJudge

class OmokGame(
    private val omokGameView: OmokGameListener,
) {
    private var previousPoint: Point? = null
    private var currentStoneColor: PointState = PointState.BLACK

    fun play(judge: OmokRuleJudge) {
        val board = Board(judge = judge)
        omokGameView.onStartGame()
        omokGameView.onBoardUpdated(board)

        playTurn(board)
    }

    private fun playTurn(board: Board) {
        while (true) {
            if (!placeStone(board)) break
        }

        showWinColor(board)
    }

    private fun placeStone(board: Board): Boolean =
        retryOnException(
            action = {
                val point = getNextPoint()
                val placeResult = board.placeStone(point, currentStoneColor)
                handlePlaceResult(placeResult, board)
            },
            shouldRetry = { it },
        )

    private fun handlePlaceResult(
        result: PlaceStoneResult,
        board: Board,
    ): Boolean {
        return when (result) {
            is PlaceStoneResult.Failure -> handlePlaceFailure(result)
            is PlaceStoneResult.Success -> handlePlaceSuccess(result, board)
        }
    }

    private fun handlePlaceFailure(result: PlaceStoneResult.Failure): Boolean {
        val message =
            when (result) {
                is PlaceStoneResult.Failure.AlreadyPlaced -> ALREADY_PLACED_ERROR_MESSAGE
                is PlaceStoneResult.Failure.Closed -> CLOSED_ERROR_MESSAGE
                is PlaceStoneResult.Failure.InvalidPoint -> INVALID_POINT_ERROR_MESSAGE
                else -> ""
            }

        message.let { omokGameView.onError(it) }
        return true
    }

    private fun handlePlaceSuccess(
        result: PlaceStoneResult.Success,
        board: Board,
    ): Boolean {
        previousPoint = result.point
        currentStoneColor = currentStoneColor.reverseStoneColor()
        omokGameView.onBoardUpdated(board)

        return if (result is PlaceStoneResult.Success.Finished) false else true
    }

    private fun showWinColor(board: Board) {
        val color = board.findPoint(previousPoint!!)?.second
        omokGameView.onGameWon(color)
    }

    private fun getNextPoint(): Point =
        retryOnException(
            action = { omokGameView.onRequestPosition(previousPoint to currentStoneColor) },
        )

    private fun <T> retryOnException(
        action: () -> T,
        shouldRetry: (T) -> Boolean = { false },
    ) = omok.utils.retry(
        action = action,
        shouldRetry = shouldRetry,
        onFailure = {
            omokGameView.onError(it.message.toString())
        },
    )

    companion object {
        private const val ALREADY_PLACED_ERROR_MESSAGE = "중복되는 칸에 돌을 둘 수 없습니다."
        private const val CLOSED_ERROR_MESSAGE = "둘 수 없는 자리입니다."
        private const val INVALID_POINT_ERROR_MESSAGE = "바둑판 크기를 벗어난 위치입니다."
    }
}
