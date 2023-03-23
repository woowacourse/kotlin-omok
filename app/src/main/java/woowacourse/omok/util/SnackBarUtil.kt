package woowacourse.omok.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

object SnackBarUtil {

    fun defaultSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun shortSnackBar(view: View, message: String) {
        showSnackBar(view, message, Snackbar.LENGTH_SHORT)
    }

    fun longSnackBar(view: View, message: String) {
        showSnackBar(view, message, Snackbar.LENGTH_LONG)
    }

    private fun showSnackBar(view: View, message: String, duration: Int) {
        Snackbar.make(view, message, duration)
            .setAnchorView(view)
            .show()
    }
}
