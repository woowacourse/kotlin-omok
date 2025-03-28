package woowacourse.omok.view

import android.widget.Toast
import woowacourse.omok.R
import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.result.Finished
import woowacourse.omok.domain.board.result.OnGoing
import woowacourse.omok.domain.board.result.PlaceStoneResult

class GameEventListenerImpl(
    private val activity: MainActivity,
) : GameEventListener {
    override fun onBoardUpdated(
        point: Point,
        state: CellState,
    ) {
        activity.updateBoardUI(point, state)
        activity.movesDao.saveMove(1, point to state)
    }

    override fun onGameWon(winnerState: CellState?) {
        activity.showGameOverDialog(winnerState)
    }

    override fun onShowMessage(result: PlaceStoneResult) {
        val messageRes =
            when (result) {
                is OnGoing.AlreadyPlaced -> R.string.already_placed_error_message
                is OnGoing.RuleViolation -> R.string.violation_error_message
                is OnGoing.InvalidMove -> R.string.invalid_point_error_message
                is Finished.BoardFull -> R.string.board_full_error_message
                else -> null
            }
        messageRes?.let { Toast.makeText(activity, activity.getString(it), Toast.LENGTH_SHORT).show() }
    }
}
