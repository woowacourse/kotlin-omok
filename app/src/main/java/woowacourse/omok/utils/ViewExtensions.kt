package woowacourse.omok.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

fun Context.createAlertDialog(builder: AlertDialog.Builder.() -> Unit): AlertDialog =
    AlertDialog.Builder(this).apply {
        setCancelable(false)
        builder()
    }.create()

fun AlertDialog.Builder.message(text: String) {
    this.setMessage(text)
}

fun AlertDialog.Builder.positiveButton(text: String = "네", onClick: (dialogInterface: DialogInterface) -> Unit = {}) {
    this.setNegativeButton(text) { dialogInterface, _ -> onClick(dialogInterface) }
}

fun AlertDialog.Builder.negativeButton(text: String = "아니오", onClick: (dialogInterface: DialogInterface) -> Unit = {}) {
    this.setPositiveButton(text) { dialogInterface, _ -> onClick(dialogInterface) }
}
