package woowacourse.omok.view

import android.widget.TableLayout
import android.widget.Toast
import omok.domain.board.OmokBoard
import omok.domain.place.Black
import omok.domain.place.Empty
import omok.domain.place.Place
import omok.domain.place.Protected
import omok.domain.place.White
import omok.view.ext.toLabel
import woowacourse.omok.R
import woowacourse.omok.view.ext.getPointAt
import woowacourse.omok.view.ext.setView

class OmokView(
    private val layout: TableLayout,
) {
    fun printInvalidInput(message: String) {
        Toast.makeText(layout.context, message, Toast.LENGTH_SHORT).show()
    }

    fun printInfoWhenFinished(winner: Place?) {
        winner?.let {
            Toast.makeText(layout.context, winnerText(it), Toast.LENGTH_SHORT).show()
        } ?: Toast.makeText(layout.context, DRAW_MESSAGE, Toast.LENGTH_SHORT).show()
    }

    fun printBoard(omokBoard: OmokBoard) {
        layout.setView { x, y, view ->
            when (omokBoard.getPointAt(x, y)) {
                is Empty -> view.setImageResource(NONE_IMAGE)
                is Protected -> view.setImageResource(R.drawable.protected_stone)
                is Black -> view.setImageResource(R.drawable.black_stone)
                is White -> view.setImageResource(R.drawable.white_stone)
            }
        }
    }

    companion object {
        const val DRAW_MESSAGE = "비겼습니다"
        const val NONE_IMAGE = 0

        private fun winnerText(winner: Place): String = "${winner.toLabel()}이 이겼습니다"
    }
}
