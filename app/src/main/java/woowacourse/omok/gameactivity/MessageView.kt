package woowacourse.omok.gameactivity

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import domain.domain.OmokGame
import woowacourse.omok.R
import kotlin.properties.Delegates

class MessageView(private val value: TextView, omokGame: OmokGame) {

    private var message: String by Delegates.observable("") { _, _, new ->
        value.text = new
    }

    init {
        value.text = value.getString(R.string.start_message)
    }

    fun printError() {
        message = value.getString(R.string.turn_error_message)
    }

    fun printRequestPosition() {
        message = value.getString(R.string.request_position_message)
    }

    private fun View.getString(@StringRes res: Int) = context.getString(res)
}
