package woowacourse.omok.view

import android.app.Activity
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

class OutputAppView(
    private val mainActivity: Activity,
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
        toastShowUp(SUSPENDED_GAME_RECOVER_MESSAGE)
    }

    fun stoneUiDraw(
        stoneColor: StoneColor,
        view: ImageView,
    ) {
        mainActivity.runOnUiThread {
            when (stoneColor) {
                StoneColor.BLACK -> view.setImageResource(R.drawable.black_stone)
                StoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
            }
        }
    }

    fun positionStatusAlert(positionState: PositionStatus) {
        when (positionState) {
            PLACED -> toastShowUp(ERROR_STONE_ALREADY_EXITS)
            OUT_OF_RANGE -> toastShowUp(ERROR_OUT_OF_RANGE)
            EMPTY -> {}
        }
    }

    fun foulAlert(foul: RenjuFoul) {
        when (foul) {
            THREE_BY_THREE_FOUL -> toastShowUp(ERROR_THREE_BY_THREE_FOUL)
            FOUR_BY_FOUR_FOUL -> toastShowUp(ERROR_FOUR_BY_FOUR_FOUL)
            OVER_FIVE_FOUL -> toastShowUp(ERROR_OVER_FIVE_FOUL)
            SAFE -> {}
        }
    }

    private fun toastShowUp(message: String) {
        mainActivity.runOnUiThread {
            Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun omokDialogAlert(
        stoneColor: StoneColor,
        restartGame: () -> Unit,
        resetGame: () -> Unit,
    ) {
        val stoneColorText = stoneColorText(stoneColor)
        (mainActivity).runOnUiThread {
            AlertDialog
                .Builder(mainActivity)
                .setTitle(NORMAL_DIALOG_TITLE)
                .setMessage(WIN_DIALOG_MESSAGE.format(stoneColorText))
                .setPositiveButton(RETRY_BUTTON_TEXT) { _, _ ->
                    restartGame()
                }.setNegativeButton(EXIT_BUTTON_TEXT) { _, _ ->
                    resetGame()
                    mainActivity.finish()
                }.setCancelable(false)
                .show()
        }
    }

    fun turnInfoUiUpdate(stoneColor: StoneColor) {
        mainActivity.runOnUiThread {
            val gameInfoView = mainActivity.findViewById<TextView>(R.id.game_info_text)
            gameInfoView.text = NEXT_TURN_MESSAGE.format(stoneColorText(stoneColor))
        }
    }

    private fun stoneColorText(stoneColor: StoneColor?): String =
        when (stoneColor) {
            StoneColor.BLACK -> BLACK_STONE_KOREAN_TEXT
            StoneColor.WHITE -> WHITE_STONE_KOREAN_TEXT
            else -> ""
        }

    fun stoneUiClear() {
        mainActivity.runOnUiThread {
            val board = mainActivity.findViewById<TableLayout>(R.id.board)
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

    fun gameEndDialogAlert(resetDatabase: () -> Unit) {
        (mainActivity).runOnUiThread {
            AlertDialog
                .Builder(mainActivity)
                .setTitle(NORMAL_DIALOG_TITLE)
                .setMessage(EXIT_CONFIRMATION_DIALOG_MESSAGE)
                .setNeutralButton(CANCEL_BUTTON_TEXT) { dialog, _ ->
                    dialog.dismiss()
                }.setNegativeButton(EXIT_BUTTON_TEXT) { _, _ ->
                    resetDatabase()
                    mainActivity.finish()
                }.setPositiveButton(SAVE_EXIT_BUTTON_TEXT) { _, _ ->
                    mainActivity.finish()
                }.show()
        }
    }

    companion object {
        private const val NEXT_TURN_MESSAGE = "%s의 차례 입니다"

        private const val NORMAL_DIALOG_TITLE = "알림"
        private const val ERROR_THREE_BY_THREE_FOUL = "3-3 반칙이 발생했습니다"
        private const val ERROR_FOUR_BY_FOUR_FOUL = "4-4 반칙이 발생했습니다"
        private const val ERROR_OVER_FIVE_FOUL = "장목 반칙이 발생했습니다"

        private const val ERROR_STONE_ALREADY_EXITS = "해당하는 위치에 돌이 존재합니다"
        private const val ERROR_OUT_OF_RANGE = "돌이 보드의 범위를 벗어났습니다"
        private const val BLACK_STONE_KOREAN_TEXT = "흑"
        private const val WHITE_STONE_KOREAN_TEXT = "백"

        private const val WIN_DIALOG_MESSAGE = "%s이 우승했습니다"
        private const val EXIT_CONFIRMATION_DIALOG_MESSAGE = "게임을 종료하시겠습니까?"

        private const val SUSPENDED_GAME_RECOVER_MESSAGE = "중단된 게임을 불러왔습니다"

        private const val RETRY_BUTTON_TEXT = "다시하기"
        private const val CANCEL_BUTTON_TEXT = "취소"
        private const val SAVE_EXIT_BUTTON_TEXT = "저장하고 종료"
        private const val EXIT_BUTTON_TEXT = "종료"
    }
}
