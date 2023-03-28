package woowacourse.omok

import android.content.Context
import android.widget.Toast

object NonDelayToast {
    private var toast: Toast? = null

    fun show(context: Context, text: String) {
        toast?.cancel()
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast?.show()
    }
}