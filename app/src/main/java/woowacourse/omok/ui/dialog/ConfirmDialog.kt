package woowacourse.omok.ui.dialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import woowacourse.omok.R

class ConfirmDialog(
    private val winnerMessage: String,
    private val onClickFinish: () -> Unit,
    private val onClickRetry: () -> Unit,
) : DialogFragment(R.layout.confirm_dialog) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val tvWinner = view.findViewById<TextView>(R.id.tvWinner)
        val btnRetry = view.findViewById<Button>(R.id.btnRetry)
        val btnFinish = view.findViewById<Button>(R.id.btnFinish)

        tvWinner.text = winnerMessage

        btnFinish.setOnClickListener {
            onClickFinish()
            dismiss()
        }

        btnRetry.setOnClickListener {
            onClickRetry()
            dismiss()
        }
    }
}
