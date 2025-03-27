package woowacourse.omok.view

import android.app.Activity
import android.app.AlertDialog
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

class OutputAppView(
    private val mainActivity: Activity,
) {
    fun updateTurnStoneColor(stoneColor: StoneColor) {
        mainActivity.runOnUiThread {
            val gameInfoView = mainActivity.findViewById<TextView>(R.id.game_info_text)
            gameInfoView.text = NEXT_TURN_MESSAGE.format(stoneColorText(stoneColor))
        }
    }

    fun resetMainView() {
        mainActivity.runOnUiThread {
            mainActivity.findViewById<View>(R.id.main).invalidate()
        }
    }

    fun printFoul(foul: RenjuFoul) {
        when (foul) {
            THREE_BY_THREE_FOUL -> printToast(ERROR_THREE_BY_THREE_FOUL)
            FOUR_BY_FOUR_FOUL -> printToast(ERROR_FOUR_BY_FOUR_FOUL)
            OVER_FIVE_FOUL -> printToast(ERROR_OVER_FIVE_FOUL)
            SAFE -> {}
        }
    }

    fun printPositionStatus(positionState: PositionStatus) {
        when (positionState) {
            PLACED -> printToast(ERROR_STONE_ALREADY_EXITS)
            OUT_OF_RANGE -> printToast(ERROR_OUT_OF_RANGE)
            EMPTY -> {}
        }
    }

    fun showStone(
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

    fun omokAlert(
        stoneColor: StoneColor,
        restartGame: () -> Unit,
    ) {
        val stoneColorText = stoneColorText(stoneColor)
        (mainActivity).runOnUiThread {
            AlertDialog
                .Builder(mainActivity)
                .setTitle(NORMAL_DIALOG_TITLE)
                .setMessage(WIN_MESSAGE.format(stoneColorText))
                .setPositiveButton("다시하기") { _, _ ->
                    restartGame()
                }.setNegativeButton("종료하기") { _, _ ->
                    mainActivity.finish()
                }.show()
        }
    }

    private fun printToast(message: String) {
        mainActivity.runOnUiThread {
            Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun stoneColorText(stoneColor: StoneColor?): String =
        when (stoneColor) {
            StoneColor.BLACK -> BLACK_STONE_KOREAN_TEXT
            StoneColor.WHITE -> WHITE_STONE_KOREAN_TEXT
            else -> ""
        }

    companion object {
        private const val NEXT_TURN_MESSAGE = "%s의 차례 입니다."
        private const val NEXT_TURN_WITH_LAST_STONE_MESSAGE = "%s의 차례 입니다. (마지막 돌의 위치: %s)"
        private const val WIN_MESSAGE = "%s이 우승했습니다."

        private const val NORMAL_DIALOG_TITLE = "알림"
        private const val ERROR_THREE_BY_THREE_FOUL = "3-3 반칙이 발생했습니다"
        private const val ERROR_FOUR_BY_FOUR_FOUL = "4-4 반칙이 발생했습니다"
        private const val ERROR_OVER_FIVE_FOUL = "장목 반칙이 발생했습니다"

        private const val ERROR_STONE_ALREADY_EXITS = "해당하는 위치에 돌이 존재합니다"
        private const val ERROR_OUT_OF_RANGE = "돌이 보드의 범위를 벗어났습니다"
        private const val BLACK_STONE_KOREAN_TEXT = "흑"
        private const val WHITE_STONE_KOREAN_TEXT = "백"
    }
}
