package woowacourse.omok.util

import android.content.Context
import android.widget.Toast

object ContextUtil {
    private var toast: Toast? = null

    fun Context.shortToast(message: String) {
        showToast(this, message, Toast.LENGTH_SHORT)
    }

    fun Context.longToast(message: String) {
        showToast(this, message, Toast.LENGTH_LONG)
    }

    private fun showToast(context: Context, message: String, duration: Int) {
        toast = if (toast == null) {
            Toast.makeText(context, message, duration)
        } else {
            toast!!.cancel()
            Toast.makeText(context, message, duration)
        }

        toast!!.show()
    }
}
