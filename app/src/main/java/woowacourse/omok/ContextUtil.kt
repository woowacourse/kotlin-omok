package woowacourse.omok

import android.content.Context
import android.widget.Toast

object ContextUtil {
    fun Context.shortToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun Context.longToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
