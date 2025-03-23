package omok.model

import omok.controller.OmokGameListener
import omok.model.board.Board
import omok.model.board.BoardPoints
import omok.model.board.BoardSize
import omok.model.board.PlaceStoneResult
import omok.model.board.Point
import omok.model.board.PointState
import omok.model.rule.OmokRuleJudge

class OmokGame(
    private val omokGameView: OmokGameListener,
) {
    private var previousPoint: Point? = null
    private var currentStoneColor: PointState = PointState.BLACK

    fun play(
        boardSize: BoardSize,
        judge: OmokRuleJudge,
    ) {
        val board = Board(BoardPoints(boardSize), judge)
        omokGameView.onStartGame()
        omokGameView.onBoardUpdated(board)

        playTurns(board)
        showWinColor(board)
    }

    private fun playTurns(board: Board): Boolean =
        retryOnException(
            action = {
                val point = getNextPoint()
                val placeResult = board.placeStone(point, currentStoneColor)
                handlePlaceResult(placeResult, board)
            },
            shouldRetry = { it },
        )

    private fun getNextPoint(): Point =
        retryOnException(
            action = { omokGameView.onRequestPosition(previousPoint to currentStoneColor) },
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

    private fun handlePlaceSuccess(
        result: PlaceStoneResult.Success,
        board: Board,
    ): Boolean {
        previousPoint = result.point
        currentStoneColor = currentStoneColor.reverseStoneColor()
        omokGameView.onBoardUpdated(board)

        return result !is PlaceStoneResult.Success.Finished
    }

    private fun handlePlaceFailure(result: PlaceStoneResult.Failure): Boolean {
        val message =
            when (result) {
                is PlaceStoneResult.Failure.AlreadyPlaced -> ALREADY_PLACED_ERROR_MESSAGE
                is PlaceStoneResult.Failure.Closed -> CLOSED_ERROR_MESSAGE
                is PlaceStoneResult.Failure.InvalidPoint -> INVALID_POINT_ERROR_MESSAGE
            }

        omokGameView.onError(message)
        return true
    }

    private fun showWinColor(board: Board) {
        previousPoint?.let { point ->
            val color = board.findPointState(point)
            omokGameView.onGameWon(color)
        }
    }

    private fun <T> retryOnException(
        action: () -> T,
        shouldRetry: (T) -> Boolean = { false },
    ) = omok.utils.retry(
        action = action,
        shouldRetry = shouldRetry,
        onFailure = { omokGameView.onError(it.message.toString()) },
    )

    companion object {
        private const val ALREADY_PLACED_ERROR_MESSAGE = "중복되는 칸에 돌을 둘 수 없습니다."
        private const val CLOSED_ERROR_MESSAGE = "둘 수 없는 자리입니다."
        private const val INVALID_POINT_ERROR_MESSAGE = "바둑판 크기를 벗어난 위치입니다."
    }
}
