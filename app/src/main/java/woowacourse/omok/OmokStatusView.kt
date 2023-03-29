package woowacourse.omok

import android.widget.TextView
import omok.OmokPoint
import omok.state.BlackStoneState
import omok.state.StoneState

class OmokStatusView(
    private val value: TextView
) {
    fun onSuccessProcess(point: OmokPoint, stoneState: StoneState) {
        setStatus("마지막 ${determineStoneColor(stoneState)}의 좌표 : (${point.x}, ${point.y})")
    }

    fun onFailedProcess(message: String) {
        setStatus(message)
    }

    private fun setStatus(message: String) {
        value.text = message
    }

    private fun determineStoneColor(stoneState: StoneState): String =
        when (stoneState) {
            BlackStoneState -> "흑"
            else -> "백"
        }
}
