package woowacourse.omok.util

import android.content.Context
import android.widget.Toast

fun Context.shortToastWithInt(message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.shortToastWithString(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.customShortToast(message: String, ) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
