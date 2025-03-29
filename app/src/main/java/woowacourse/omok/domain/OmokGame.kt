package woowacourse.omok.domain

import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.result.Finished
import woowacourse.omok.domain.board.result.OnGoing
import woowacourse.omok.domain.board.result.PlaceStoneResult
import woowacourse.omok.view.omok.GameEventListener

class OmokGame(
    private val eventListener: GameEventListener,
) {
    val state: GameState = GameState()

    fun start(
        lastMove: Pair<Point, CellState>?,
        isFinished: Boolean,
    ) {
        state.start(lastMove, isFinished)
    }

    fun placeStone(
        board: Board,
        point: Point,
    ) {
        if (state.isFinished) return
        val placeResult = board.placeStone(point, state.currentCellState)
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
                state.finishGame()
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
        state.updateState(point)
        eventListener.onBoardUpdated(point, state.currentCellState)
    }

    private fun showWinColor(board: Board) {
        state.previousPoint?.let { point ->
            val color = board.findStoneColor(point)
            eventListener.onGameWon(color)
        }
    }

    private fun showMessage(result: PlaceStoneResult) = eventListener.onShowMessage(result)
}
