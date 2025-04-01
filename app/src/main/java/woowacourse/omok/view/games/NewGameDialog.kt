package woowacourse.omok.view.games

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.widget.EditText
import woowacourse.omok.R

class NewGameDialog(
    context: Context,
    private val onGameCreated: (String) -> Unit,
) {
    private val dialog: AlertDialog

    init {
        val inputField = createInputField(context)
        dialog = createDialog(context, inputField)
    }

    private fun createInputField(context: Context): EditText =
        EditText(context).apply {
            inputType = InputType.TYPE_CLASS_TEXT
        }

    private fun createDialog(
        context: Context,
        inputField: EditText,
    ): AlertDialog =
        AlertDialog
            .Builder(context)
            .setTitle(R.string.dialog_create_game_title)
            .setView(inputField)
            .setPositiveButton(R.string.dialog_create_game_button_positive) { _, _ ->
                val roomName = inputField.text.toString()
                onGameCreated(roomName)
            }.setNegativeButton(R.string.dialog_create_game_button_negative) { dialog, _ ->
                dialog.cancel()
            }.create()

    fun show() {
        dialog.show()
    }
}
