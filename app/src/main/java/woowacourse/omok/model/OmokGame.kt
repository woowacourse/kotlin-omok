package woowacourse.omok.model

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Point
import woowacourse.omok.model.board.StoneColor
import woowacourse.omok.model.board.result.Finished
import woowacourse.omok.model.board.result.OnGoing
import woowacourse.omok.model.board.result.PlaceStoneResult
import woowacourse.omok.view.OmokGameListener

class OmokGame(
    private val omokGameListener: OmokGameListener,
) {
    private var previousPoint: Point? = null
    private var currentStoneColor: StoneColor = StoneColor.BLACK

    fun start() {
        omokGameListener.onStartGame()
        previousPoint = null
        currentStoneColor = StoneColor.BLACK
    }

    fun placeStone(
        board: Board,
        point: Point,
    ): PlaceStoneResult {
        val placeResult = board.placeStone(point, currentStoneColor)
        handlePlaceResult(placeResult, board)
        return placeResult
    }

    private fun handlePlaceResult(
        result: PlaceStoneResult,
        board: Board,
    ) {
        if (result is OnGoing) handleOnGoingResult(result)
        if (result is Finished) handleFinishedResult(result, board)
    }

    private fun handleFinishedResult(
        result: Finished,
        board: Board,
    ) {
        when (result) {
            is Finished.GameFinished -> {
                updateGameState(result.point)
                showWinColor(board)
            }

            is Finished.BoardFull -> {
                updateGameState(result.point)
                omokGameListener.onError(BOARD_FULL_ERROR_MESSAGE)
            }
        }
    }

    private fun handleOnGoingResult(result: OnGoing) {
        when (result) {
            is OnGoing.StonePlaced -> updateGameState(result.point)
            is OnGoing.AlreadyPlaced -> omokGameListener.onError(ALREADY_PLACED_ERROR_MESSAGE)
            is OnGoing.RuleViolation -> omokGameListener.onError(CLOSED_ERROR_MESSAGE)
            is OnGoing.InvalidMove -> omokGameListener.onError(INVALID_POINT_ERROR_MESSAGE)
        }
    }

    private fun updateGameState(point: Point) {
        previousPoint = point
        omokGameListener.onBoardUpdated(point, currentStoneColor)
        currentStoneColor = currentStoneColor.reverseStoneColor()
    }

    private fun showWinColor(board: Board) {
        previousPoint?.let { point ->
            val color = board.findStoneColor(point)
            omokGameListener.onGameWon(color)
        }
    }

    companion object {
        private const val ALREADY_PLACED_ERROR_MESSAGE = "중복되는 칸에 돌을 둘 수 없습니다."
        private const val CLOSED_ERROR_MESSAGE = "둘 수 없는 자리입니다."
        private const val INVALID_POINT_ERROR_MESSAGE = "바둑판 크기를 벗어난 위치입니다."
        private const val BOARD_FULL_ERROR_MESSAGE = "무승부! - 바둑판에 더 이상 둘 수 있는 공간이 없습니다."
    }
}
