package woowacourse.omok.view

import android.widget.Toast
import woowacourse.omok.R
import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.result.Finished
import woowacourse.omok.domain.board.result.OnGoing
import woowacourse.omok.domain.board.result.PlaceStoneResult
import java.lang.ref.WeakReference

class GameEventListenerImpl(
    activity: MainActivity,
) : GameEventListener {
    private val weakActivity = WeakReference(activity)

    override fun onBoardUpdated(
        point: Point,
        state: CellState,
    ) {
        weakActivity.get()?.let { activity ->
            activity.updateBoardUI(point, state)
            activity.movesDao.saveMove(1, point to state)
        }
    }

    override fun onGameWon(winnerState: CellState?) {
        weakActivity.get()?.showGameOverDialog(winnerState)
    }

    override fun onShowMessage(result: PlaceStoneResult) {
        weakActivity.get()?.let { activity ->
            val messageRes =
                when (result) {
                    is OnGoing.AlreadyPlaced -> R.string.already_placed_error_message
                    is OnGoing.RuleViolation -> R.string.violation_error_message
                    is OnGoing.InvalidMove -> R.string.invalid_point_error_message
                    is Finished.BoardFull -> R.string.board_full_error_message
                    else -> null
                }

            messageRes?.let {
                Toast.makeText(activity, activity.getString(it), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
