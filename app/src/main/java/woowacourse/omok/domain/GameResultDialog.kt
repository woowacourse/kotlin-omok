package woowacourse.omok.domain

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import woowacourse.omok.MainActivity
import woowacourse.omok.R
import woowacourse.omok.domain.db.OmokRepository
import woowacourse.omok.domain.rule.GameResult

class GameResultDialog(
    private val context: Context,
    private val omokRepository: OmokRepository,
) {
    fun popUp(gameResult: GameResult) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle("게임결과")
            .setMessage(displayGameResultMessage(gameResult))
            .setPositiveButton("한번 더하기") { dialog, _ ->
                dialog.dismiss()
                omokRepository.resetDatabase()
                context.startActivity(Intent(context, MainActivity::class.java))
                if (context is Activity) context.finish()
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun displayGameResultMessage(result: GameResult): String {
        return when (result) {
            GameResult.DRAW -> context.getString(R.string.DRAW_RESULT_MESSAGE)
            GameResult.WIN_BLACK, GameResult.WIN_WHITE -> context.getString(R.string.WIN_RESULT_MESSAGE).format(result.toLabel())
        }
    }

    private fun GameResult.toLabel(): String =
        when (this) {
            GameResult.WIN_BLACK -> context.getString(R.string.BLACK_COLOR_LABEL)
            GameResult.WIN_WHITE -> context.getString(R.string.WHITE_COLOR_LABEL)
            GameResult.DRAW -> ""
        }
}
