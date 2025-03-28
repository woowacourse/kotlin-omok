package omok.event

import android.content.Context
import android.widget.TableLayout
import android.widget.Toast
import omok.domain.board.OmokBoard
import omok.domain.place.Place

class OmokEventListener(private val board: TableLayout, val context: Context) : GameEventListner {
    override fun onFinished(winner: Place) {
        Toast.makeText(context, "토스트 메시지입니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onGameStart() {
    }

    override fun onInputRequest(place: Place): String {
        return ""
    }

    override fun onBoardView(omokBoard: OmokBoard) {
    }
}
