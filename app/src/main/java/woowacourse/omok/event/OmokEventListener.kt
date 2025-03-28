package omok.event

import android.content.Context
import android.widget.TableLayout
import android.widget.Toast
import kotlinx.coroutines.future.await
import omok.domain.board.OmokBoard
import omok.domain.place.Place
import woowacourse.omok.event.Board
import java.util.concurrent.CompletableFuture

class OmokEventListener(private val board: TableLayout, val context: Context) : GameEventListner {
    override fun onFinished(winner: Place) {
        Toast.makeText(context, "토스트 메시지입니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onGameStart() {
    }

    override suspend fun onInputRequest(place: Place): String {
        val future = CompletableFuture<String>()
        Board.setBoard(place, board, future)
        future.await()
        return future.get()
    }

    override fun onBoardView(omokBoard: OmokBoard) {
        Board.printBoard(board, omokBoard)
    }
}
