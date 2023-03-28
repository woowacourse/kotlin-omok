package woowacourse.omok

import android.widget.TextView
import omok.OmokPoint

class OmokStatusView(
    private val value: TextView
) {
    fun onSuccessProcess(point: OmokPoint) {
        setStatus("마지막 좌표 : (${point.x}, ${point.y})")
    }

    fun onFailedProcess(message: String) {
        setStatus(message)
    }

    private fun setStatus(message: String) {
        value.text = message
    }
}
