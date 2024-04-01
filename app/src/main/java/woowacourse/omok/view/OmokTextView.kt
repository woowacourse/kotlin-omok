package woowacourse.omok.view

import android.widget.TextView
import woowacourse.omok.model.Board
import woowacourse.omok.model.OmokStone
import woowacourse.omok.util.getNextTurnColor
import woowacourse.omok.util.mapStoneColorToString

class OmokTextView(private val textView: TextView) : OmokOutputView {
    override fun showStartMessage() {
        textView.text = "게임을 시작합니다!"
    }

    override fun showProgress(stone: OmokStone?) {
        val nextColor = getWhoIsNext(stone)
        val lastPosition = getLastPositionOrBlank(stone)
        val resultString = nextColor + lastPosition
        textView.text = resultString
    }

    private fun getWhoIsNext(stone: OmokStone?): String {
        return if (stone != null) {
            getNextTurnColor(stone)
        } else {
            "흑"
        } + "돌의 차례입니다"
    }

    private fun getLastPositionOrBlank(stone: OmokStone?): String {
        return if (stone != null) {
            "(마지막 돌의 위치: %s)".format(stone.position.toString())
        } else {
            ""
        }
    }

    override fun showGameResult(
        board: Board,
        stone: OmokStone,
    ) {
        val winnerColor = mapStoneColorToString(stone.color)
        val result = "${winnerColor}돌 승리!"
        textView.text = result
    }

    override fun reset() {
        textView.text = ""
    }
}
