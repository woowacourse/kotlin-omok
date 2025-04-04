package woowacourse.omok.view

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import woowacourse.omok.R
import woowacourse.omok.model.board.PositionStatus
import woowacourse.omok.model.board.PositionStatus.EMPTY
import woowacourse.omok.model.board.PositionStatus.OUT_OF_RANGE
import woowacourse.omok.model.board.PositionStatus.PLACED
import woowacourse.omok.model.rule.RenjuFoul
import woowacourse.omok.model.rule.RenjuFoul.FOUR_BY_FOUR_FOUL
import woowacourse.omok.model.rule.RenjuFoul.OVER_FIVE_FOUL
import woowacourse.omok.model.rule.RenjuFoul.SAFE
import woowacourse.omok.model.rule.RenjuFoul.THREE_BY_THREE_FOUL
import woowacourse.omok.model.stone.StoneColor
import woowacourse.omok.model.stone.position.Position

class GameActivityOutputView(
    private val gameActivity: Activity,
) {
    fun stonesUiDraw(
        stones: Map<Position, StoneColor>,
        positionViews: Map<Position, ImageView>,
    ) {
        stones.forEach { (position, stoneColor) ->
            positionViews[position]?.let {
                stoneUiDraw(stoneColor, it)
            }
        }
    }

    fun recoveryStonesAlert() {
        toastShowUp(gameActivity.getString(R.string.suspended_game_recover_message))
    }

    fun stoneUiDraw(
        stoneColor: StoneColor,
        view: ImageView,
    ) {
        gameActivity.runOnUiThread {
            when (stoneColor) {
                StoneColor.BLACK -> view.setImageResource(R.drawable.black_stone)
                StoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
            }
        }
    }

    fun positionStatusAlert(positionState: PositionStatus) {
        when (positionState) {
            PLACED -> toastShowUp(gameActivity.getString(R.string.error_stone_already_exists))
            OUT_OF_RANGE -> toastShowUp(gameActivity.getString(R.string.error_out_of_range))
            EMPTY -> {}
        }
    }

    fun foulAlert(foul: RenjuFoul) {
        when (foul) {
            THREE_BY_THREE_FOUL -> toastShowUp(gameActivity.getString(R.string.error_three_by_three_foul))
            FOUR_BY_FOUR_FOUL -> toastShowUp(gameActivity.getString(R.string.error_four_by_four_foul))
            OVER_FIVE_FOUL -> toastShowUp(gameActivity.getString(R.string.error_over_five_foul))
            SAFE -> {}
        }
    }

    private fun toastShowUp(message: String) {
        gameActivity.runOnUiThread {
            Toast.makeText(gameActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun omokDialogAlert(
        playerName: String,
        restartGame: () -> Unit,
        omokWinnerDBWrite: () -> Unit,
        roomWithStonesDelete: () -> Unit,
        stonesDelete: () -> Unit,
    ) {
        (gameActivity).runOnUiThread {
            AlertDialog
                .Builder(gameActivity)
                .setTitle(gameActivity.getString(R.string.normal_dialog_title))
                .setMessage(gameActivity.getString(R.string.win_dialog_message).format(playerName))
                .setPositiveButton(gameActivity.getString(R.string.retry_button)) { _, _ ->
                    stonesDelete()
                    restartGame()
                }.setNegativeButton(gameActivity.getString(R.string.exit_button_text)) { _, _ ->
                    roomWithStonesDelete()
                    omokWinnerDBWrite()
                    gameActivity.setResult(RESULT_OK)
                    gameActivity.finish()
                }.setCancelable(false)
                .show()
        }
    }

    fun turnInfoUiUpdate(name: String) {
        gameActivity.runOnUiThread {
            val gameInfoView = gameActivity.findViewById<TextView>(R.id.game_info_text)
            gameInfoView.text = gameActivity.getString(R.string.next_turn_message).format(name)
        }
    }

    fun stoneUiClear() {
        gameActivity.runOnUiThread {
            val board = gameActivity.findViewById<TableLayout>(R.id.board)
            board
                .children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .forEach { positionView ->
                    positionView.setImageResource(0)
                }
        }
    }

    fun gameEndDialogAlert(roomWithStonesDelete: () -> Unit) {
        (gameActivity).runOnUiThread {
            AlertDialog
                .Builder(gameActivity)
                .setTitle(gameActivity.getString(R.string.normal_dialog_title))
                .setMessage(gameActivity.getString(R.string.exit_confirmation_dialog_message))
                .setNeutralButton(gameActivity.getString(R.string.cancel_button)) { dialog, _ ->
                    dialog.dismiss()
                }.setNegativeButton(gameActivity.getString(R.string.exit_button_text)) { _, _ ->
                    roomWithStonesDelete()
                    gameActivity.setResult(RESULT_OK)
                    gameActivity.finish()
                }.setPositiveButton(gameActivity.getString(R.string.save_exit_button_text)) { _, _ ->
                    gameActivity.setResult(RESULT_OK)
                    gameActivity.finish()
                }.show()
        }
    }
}
