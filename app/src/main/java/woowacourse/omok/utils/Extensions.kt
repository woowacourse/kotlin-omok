package woowacourse.omok.utils

import android.content.Context
import android.content.DialogInterface
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

fun Context.createAlertDialog(builder: AlertDialog.Builder.() -> Unit): AlertDialog =
    AlertDialog.Builder(this).apply {
        setCancelable(false)
        builder()
    }.create()

fun AlertDialog.Builder.message(text: String) {
    this.setMessage(text)
}

fun AlertDialog.Builder.positiveButton(
    text: String = "네",
    onClick: (dialogInterface: DialogInterface) -> Unit = {},
) {
    this.setNegativeButton(text) { dialogInterface, _ -> onClick(dialogInterface) }
}

fun AlertDialog.Builder.negativeButton(
    text: String = "아니오",
    onClick: (dialogInterface: DialogInterface) -> Unit = {},
) {
    this.setPositiveButton(text) { dialogInterface, _ -> onClick(dialogInterface) }
}

fun ImageView.setImageByResId(@DrawableRes resId: Int) {
    this.setImageDrawable(ContextCompat.getDrawable(this.context, resId))
}

fun Context.showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
