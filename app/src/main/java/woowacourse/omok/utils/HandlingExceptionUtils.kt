package woowacourse.omok.utils

import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar

object HandlingExceptionUtils {
    fun <T> retryUntilSuccess(
        view: ImageView,
        action: () -> T,
    ): T? =
        runCatching {
            action()
        }.getOrElse {
            Snackbar.make(view, it.localizedMessage, Snackbar.LENGTH_LONG).show()
            return null
        }
}
