package woowacourse.omok.utils

import android.content.Context
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import omok.model.result.PutResult
import woowacourse.omok.R

object HandlingPutResultUtils {
    fun displayPutStatus(
        view: ImageView,
        putResult: PutResult,
        context: Context,
    ) {
        when (putResult) {
            PutResult.Running -> {}
            PutResult.Failure -> makeHintSnackBar(view, context.getString(R.string.placed_stone_position))
            PutResult.DoubleThree -> makeHintSnackBar(view, context.getString(R.string.double_three_message))
            PutResult.DoubleFour -> makeHintSnackBar(view, context.getString(R.string.double_four_message))
            PutResult.ExceedFive -> makeHintSnackBar(view, context.getString(R.string.exceed_five_message))
        }
    }

    private fun makeHintSnackBar(
        view: ImageView,
        message: String,
    ) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}
