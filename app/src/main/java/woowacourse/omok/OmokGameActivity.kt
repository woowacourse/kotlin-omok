package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import domain.CoordinateState
import domain.Position
import domain.domain.Board
import domain.domain.state.BlackTurn
import domain.domain.state.BlackWin
import domain.domain.state.State
import domain.domain.state.WhiteTurn
import domain.domain.state.WhiteWin
import domain.view.Observer
import domain.view.OmokView
import woowacourse.omok.OmokGameUtil.loopBoardTable
import woowacourse.omok.OmokGameUtil.toName
import woowacourse.omok.util.ContextUtil.longToast
import woowacourse.omok.util.SnackBarUtil.defaultSnackBar

class OmokGameActivity : AppCompatActivity(), Observer {
    private var preColor: CoordinateState = CoordinateState.BLACK
    private val boardTable by lazy { findViewById<TableLayout>(R.id.board_table) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.omok_game_activity)

        val board = Board()
        runOmokGame(board)
        synchronizeConsoleView(board)
    }

    private fun synchronizeConsoleView(board: Board) {
        board.registerObserver(OmokView())
    }

    private fun runOmokGame(board: Board) {
        board.registerObserver(this)
        setOmokClickListener(board)
    }

    private fun setOmokClickListener(board: Board) {
        loopBoardTable(boardTable) { position: Position, imageView ->
            clickPart(board, position, imageView)
        }
    }

    private fun clickPart(board: Board, position: Position, imageView: ImageView) {
        imageView.setOnClickListener {
            board.next(position)
        }
    }

    override fun update(state: State) {
        when (state) {
            is BlackTurn -> updateView(state)
            is WhiteTurn -> updateView(state)
            is BlackWin -> win(state)
            is WhiteWin -> win(state)
        }
    }

    private fun updateView(state: State) {
        when (preColor == state.getTurn()) {
            true -> defaultSnackBar(findViewById(R.id.root), "놓을 수 없는 수 입니다")
            false -> drawStone(state)
        }
    }

    private fun drawStone(state: State) {
        loopBoardTable(boardTable) { position: Position, imageView ->
            val resId = OmokGameUtil.matchColor(state.stones[position.y, position.x])
            if (resId != null) imageView.setImageResource(resId)
        }
        preColor = state.getTurn()
    }

    private fun win(state: State) {
        loopBoardTable(boardTable) { _, imageView -> imageView.isEnabled = false }
        longToast("${state.getTurn().toName()}의 승리입니다!!!")
        drawStone(state)
    }
}
