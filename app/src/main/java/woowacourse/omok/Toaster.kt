package woowacourse.omok

import android.content.Context
import android.widget.Toast

object Toaster {
    private var toast: Toast? = null
    fun showToast(context: Context, messageId: Int) {
        toast?.cancel()
        toast = Toast.makeText(context, context.getString(messageId), Toast.LENGTH_SHORT)
            .also { it.show() }
    }

    fun cancel() {
        toast?.cancel()
    }
}
