package woowacourse.omok.domain

import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.result.Finished
import woowacourse.omok.domain.board.result.OnGoing
import woowacourse.omok.domain.board.result.PlaceStoneResult
import woowacourse.omok.view.GameEventListener

class OmokGame(
    private val eventListener: GameEventListener,
) {
    private var previousPoint: Point? = null
    private var currentCellState: CellState = CellState.BLACK

    fun start(lastMove: Pair<Point, CellState>?) {
        previousPoint = lastMove?.first
        currentCellState = lastMove?.second?.reverseCellState() ?: CellState.BLACK
    }

    fun placeStone(
        board: Board,
        point: Point,
    ) {
        val placeResult = board.placeStone(point, currentCellState)
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
        eventListener.onBoardUpdated(point, currentCellState)
        currentCellState = currentCellState.reverseCellState()
    }

    private fun showWinColor(board: Board) {
        previousPoint?.let { point ->
            val color = board.findStoneColor(point)
            eventListener.onGameWon(color)
        }
    }

    private fun showMessage(result: PlaceStoneResult) = eventListener.onShowMessage(result)
}
