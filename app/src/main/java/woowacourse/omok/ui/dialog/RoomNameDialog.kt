package woowacourse.omok.ui.dialog

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import woowacourse.omok.R
import woowacourse.omok.databinding.RoomNameDialogBinding

class RoomNameDialog(
    private val onClickComplete: (String) -> Unit,
) : DialogFragment(R.layout.room_name_dialog) {
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.85).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = RoomNameDialogBinding.bind(view)

        binding.buttonCreate.setOnClickListener {
            val roomName = binding.roomNameEditText.text.toString()
            if (checkRoomName(roomName)) {
                onClickComplete(roomName)
                dismiss()
            }
        }
    }

    private fun checkRoomName(roomName: String): Boolean {
        if (roomName.isEmpty()) {
            requireContext().showToast(R.string.text_plz_input_room_name)
            Toast.makeText(requireContext(), R.string.text_plz_input_room_name, Toast.LENGTH_SHORT)
                .show()
            return false
        }
        return true
    }
}
