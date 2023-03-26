package woowacourse.omok

import android.app.Activity
import androidx.appcompat.app.AlertDialog

fun Activity.makeToastMessage(messageId: Int) = IntegratedToast.showToast(baseContext, messageId)

fun Activity.showAskDialog(
    titleId: Int,
    messageId: Int,
    actionPositive: () -> Unit = {},
    actionNegative: () -> Unit = {},
) {
    AlertDialog.Builder(this)
        .setTitle(getString(titleId))
        .setMessage(getString(messageId))
        .setNegativeButton(R.string.close) { _, _ ->
            actionNegative()
        }.setPositiveButton(R.string.confirm) { _, _ ->
            actionPositive()
        }.setCancelable(false)
        .show()
}
