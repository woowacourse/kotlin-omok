package woowacourse.omok.domain

import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.StoneColor
import woowacourse.omok.domain.board.result.Finished
import woowacourse.omok.domain.board.result.OnGoing
import woowacourse.omok.domain.board.result.PlaceStoneResult
import woowacourse.omok.view.OmokGameListener

class OmokGame(
    private val omokGameListener: OmokGameListener,
) {
    private var previousPoint: Point? = null
    private var currentStoneColor: StoneColor = StoneColor.BLACK

    fun start(lastMove: Pair<Point, StoneColor>?) {
        previousPoint = lastMove?.first
        currentStoneColor = lastMove?.second?.reverseStoneColor() ?: StoneColor.BLACK
    }

    fun placeStone(
        board: Board,
        point: Point,
    ) {
        val placeResult = board.placeStone(point, currentStoneColor)
        handlePlaceResult(placeResult, board)
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
                showMessage(result)
            }
        }
    }

    private fun handleOnGoingResult(result: OnGoing) {
        when (result) {
            is OnGoing.StonePlaced -> updateGameState(result.point)
            is OnGoing.AlreadyPlaced -> showMessage(result)
            is OnGoing.RuleViolation -> showMessage(result)
            is OnGoing.InvalidMove -> showMessage(result)
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

    private fun showMessage(result: PlaceStoneResult) = omokGameListener.onShowMessage(result)
}
