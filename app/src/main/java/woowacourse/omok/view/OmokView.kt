package woowacourse.omok.view

import android.widget.ImageView
import android.widget.TableLayout
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import woowacourse.omok.MainActivity
import woowacourse.omok.R
import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.MoveResult
import woowacourse.omok.model.Stone
import woowacourse.omok.model.position.Col
import woowacourse.omok.model.position.Position
import woowacourse.omok.model.position.Row

class OmokView {
    fun printOmokStart(boardLayout: TableLayout) {
        Snackbar.make(boardLayout, R.string.message_omok_start, Snackbar.LENGTH_SHORT).show()
    }

    fun printMoveResult(
        mainActivity: MainActivity,
        boardLayout: TableLayout,
        moveResult: MoveResult,
    ) {
        when (moveResult) {
            is MoveResult.Success -> printSuccessResult(mainActivity, moveResult)
            is MoveResult.Failure -> printFailureResult(mainActivity, boardLayout, moveResult)
        }
    }

    private fun printSuccessResult(
        mainActivity: MainActivity,
        moveResult: MoveResult.Success,
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(mainActivity)
        builder.setMessage(
            when (moveResult) {
                is MoveResult.Success.Playing -> mainActivity.getString(R.string.message_omok_in_progress)
                is MoveResult.Success.Finished ->
                    mainActivity.getString(
                        R.string.message_omok_winner,
                    ).format(moveResult.winner.toPlayerName(mainActivity))
            },
        ).show()
    }

    private fun Color.toPlayerName(mainActivity: MainActivity): String {
        return when (this) {
            Color.BLACK -> mainActivity.getString(R.string.black_player)
            Color.WHITE -> mainActivity.getString(R.string.white_player)
        }
    }

    private fun printFailureResult(
        mainActivity: MainActivity,
        boardLayout: TableLayout,
        moveResult: MoveResult.Failure,
    ) {
        val message: String =
            when (moveResult) {
                is MoveResult.Failure.PositionAlreadyOccupied -> mainActivity.getString(R.string.message_failure_position_already_occupied)
                is MoveResult.Failure.DoubleThreeViolation -> mainActivity.getString(R.string.message_failure_double_three_violation)
                is MoveResult.Failure.DoubleFourViolation -> mainActivity.getString(R.string.message_failure_double_four_violation)
                is MoveResult.Failure.OverlineViolation -> mainActivity.getString(R.string.message_failure_overline_violation)
                else -> return
            }
        Snackbar.make(boardLayout, message, Snackbar.LENGTH_SHORT).show()
    }

    fun renderStone(
        imageViews: Sequence<ImageView>,
        board: Board,
        stone: Stone,
    ) {
        val index =
            (stone.position.y.value - 1) * board.row.value + (stone.position.x.value - 1)
        imageViews.toList()[index].setImageResource(stone.color.toImage())
    }

    private fun Color.toImage(): Int =
        when (this) {
            Color.BLACK -> R.drawable.black_stone
            Color.WHITE -> R.drawable.white_stone
        }

    fun setListeners(
        imageViews: Sequence<ImageView>,
        board: Board,
        onClick: (position: Position) -> Unit,
    ) {
        imageViews.forEachIndexed { index, view ->
            val x = Col(index % board.col.value + 1)
            val y = Row(index / board.row.value + 1)
            view.setOnClickListener { onClick(Position(x, y)) }
        }
    }

    fun clearListeners(imageViews: Sequence<ImageView>) {
        imageViews.forEach { view -> view.setOnClickListener(null) }
    }
}
