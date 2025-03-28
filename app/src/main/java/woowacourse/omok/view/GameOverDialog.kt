package woowacourse.omok.view

import android.app.AlertDialog
import android.content.Context
import woowacourse.omok.R

class GameOverDialog(
    private val context: Context,
) {
    fun show(
        winnerState: String,
        onClick: () -> Unit,
    ) {
        AlertDialog
            .Builder(context)
            .setTitle(context.getString(R.string.dialog_title_game_over))
            .setMessage(context.getString(R.string.dialog_description_winner, winnerState))
            .setPositiveButton(context.getString(R.string.dialog_button_positive)) { dialog, _ ->
                dialog.dismiss()
                onClick()
            }.setCancelable(false)
            .show()
    }
}
