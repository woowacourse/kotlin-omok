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
            builder.setTitle(DIALOG_HEAD_NAME_INPUT)
            builder.setView(dialogView)

            val blackStoneInputView = dialogView.findViewById<EditText>(R.id.black_stone_name)
            val whiteStoneInputView = dialogView.findViewById<EditText>(R.id.white_stone_name)

            builder.setPositiveButton(CONFIRM_BTN_TEXT) { _, _ ->
            }

            builder.setNegativeButton(CANCEL_BTN_TEXT) { dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()

            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val blackStoneName = blackStoneInputView.text.toString()
                val whiteStoneName = whiteStoneInputView.text.toString()
                when {
                    blackStoneName.isEmpty() || whiteStoneName.isEmpty() ->
                        toastShowUp(ALERT_EMPTY_PLAYER_NAME)

                    blackStoneName == whiteStoneName -> toastShowUp(ALERT_SAME_PLAYER_NAME)

                    else -> {
                        onNamesConfirmed(blackStoneName, whiteStoneName)
                        dialog.dismiss()
                    }
                }
            }
        }
    }

    companion object {
        private const val DIALOG_HEAD_NAME_INPUT = "닉네임 입력"
        private const val ALERT_EMPTY_PLAYER_NAME = "입력값이 비었습니다!!"
        private const val ALERT_SAME_PLAYER_NAME = "두 닉네임이 같습니다!!"

        private const val CONFIRM_BTN_TEXT = "확인"
        private const val CANCEL_BTN_TEXT = "취소"
    }
}
