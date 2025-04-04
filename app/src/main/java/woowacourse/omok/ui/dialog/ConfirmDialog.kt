package woowacourse.omok.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import woowacourse.omok.R
import woowacourse.omok.databinding.ConfirmDialogBinding

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
        val binding = ConfirmDialogBinding.bind(view)

        with(binding) {
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
}
