package woowacourse.omok

import android.content.Context
import android.widget.Toast
import omok.model.OmokGameState
import omok.model.entity.StoneColor

class ResultToast(
    private val context: Context,
) : OmokStateListener {
    override fun listen(omokState: OmokGameState) {
        if (!omokState.isFinished()) {
            return
        }
        val turn = omokState.turn
        val winnerText =
            if (turn.isWin()) {
                context.getString(
                    turn.color().toStringId(),
                ) + context.getString(R.string.winning_message)
            } else {
                context.getString(R.string.draw_message)
            }
        Toast.makeText(context, winnerText, Toast.LENGTH_SHORT).show()
    }
}

fun StoneColor.toStringId() =
    when (this) {
        StoneColor.BLACK -> R.string.black
        StoneColor.WHITE -> R.string.white
    }
