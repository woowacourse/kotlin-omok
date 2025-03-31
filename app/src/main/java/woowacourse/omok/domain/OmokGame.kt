package woowacourse.omok.domain

import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.result.Finished
import woowacourse.omok.domain.board.result.OnGoing
import woowacourse.omok.domain.board.result.PlaceStoneResult
import woowacourse.omok.view.omok.GameEventListener

class OmokGame(
    private val state: GameState = GameState(),
    private val eventListener: GameEventListener,
) {
    fun previousMovePoint(): Point? = state.previousPoint

    fun isFinish(): Boolean = state.isFinished

    fun currentStoneColor(board: Board): CellState = board.findStoneColor(state.previousPoint)?.reverseCellState() ?: CellState.BLACK

    fun placeStone(
        board: Board,
        point: Point,
    ) {
        if (state.isFinished) return

        val placeResult = board.placeStone(point, currentStoneColor(board))
        handlePlaceResult(placeResult, board)
    }

    private fun handlePlaceResult(
        result: PlaceStoneResult,
        board: Board,
    ) {
        if (result is OnGoing) handleOnGoingResult(board, result)
        if (result is Finished) handleFinishedResult(board, result)
    }

    private fun handleFinishedResult(
        board: Board,
        result: Finished,
    ) {
        when (result) {
            is Finished.GameFinished -> {
                updateGameState(board, result.point)
                state.finishGame()
                showWinColor(board)
            }

            is Finished.BoardFull -> {
                updateGameState(board, result.point)
                showMessage(result)
            }
        }
    }

    private fun handleOnGoingResult(
        board: Board,
        result: OnGoing,
    ) {
        when (result) {
            is OnGoing.StonePlaced -> updateGameState(board, result.point)
            is OnGoing.AlreadyPlaced -> showMessage(result)
            is OnGoing.RuleViolation -> showMessage(result)
            is OnGoing.InvalidMove -> showMessage(result)
        }
    }

    private fun updateGameState(
        board: Board,
        point: Point,
    ) {
        state.updateLastMovePoint(point)
        eventListener.onBoardUpdated(
            point,
            currentStoneColor(board).reverseCellState(),
        )
    }

    private fun showWinColor(board: Board) {
        state.previousPoint?.let { point ->
            val color = board.findStoneColor(point)
            eventListener.onGameWon(color)
        }
    }

    private fun showMessage(result: PlaceStoneResult) = eventListener.onShowMessage(result)
}
