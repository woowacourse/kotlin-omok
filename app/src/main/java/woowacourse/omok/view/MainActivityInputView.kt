package woowacourse.omok.view

import android.app.Activity
import android.widget.EditText
import android.widget.Toast
import woowacourse.omok.R

class MainActivityInputView(
    private val mainActivity: Activity,
) {
    private fun toastShowUp(message: String) {
        mainActivity.runOnUiThread {
            Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun showPlayerNamesInputDialog(onNamesConfirmed: (String, String) -> Unit) {
        (mainActivity).runOnUiThread {
            val dialogView = mainActivity.layoutInflater.inflate(R.layout.make_room_dialog_input, null)
            val builder =
                androidx.appcompat.app.AlertDialog
                    .Builder(mainActivity)
            builder.setTitle(mainActivity.getString(R.string.new_game_name_input_dialoag_title))
            builder.setView(dialogView)

            val blackStoneInputView = dialogView.findViewById<EditText>(R.id.black_stone_name)
            val whiteStoneInputView = dialogView.findViewById<EditText>(R.id.white_stone_name)

            builder.setPositiveButton(mainActivity.getString(R.string.ok_button)) { _, _ ->
            }

            builder.setNegativeButton(mainActivity.getString(R.string.cancel_button)) { dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()

            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val blackStoneName = blackStoneInputView.text.toString()
                val whiteStoneName = whiteStoneInputView.text.toString()
                when {
                    blackStoneName.isEmpty() || whiteStoneName.isEmpty() ->
                        toastShowUp(mainActivity.getString(R.string.empty_input))

                    blackStoneName == whiteStoneName -> toastShowUp(mainActivity.getString(R.string.duplicate_name))

                    else -> {
                        onNamesConfirmed(blackStoneName, whiteStoneName)
                        dialog.dismiss()
                    }
                }
            }
        }
    }
}
