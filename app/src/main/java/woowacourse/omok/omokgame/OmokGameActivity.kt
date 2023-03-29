package woowacourse.omok.omokgame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import domain.CoordinateState
import domain.CoordinateState.BLACK
import domain.Position
import domain.domain.Board
import domain.domain.state.BlackTurn
import domain.domain.state.BlackWin
import domain.domain.state.State
import domain.domain.state.WhiteTurn
import domain.domain.state.WhiteWin
import domain.view.Observer
import domain.view.OmokView
import woowacourse.omok.R
import woowacourse.omok.home.HomeActivity.Companion.ROOM_ID
import woowacourse.omok.omokgame.OmokGameUtil.loopBoardTable
import woowacourse.omok.result.ResultActivity
import woowacourse.omok.util.ContextUtil.longToast
import woowacourse.omok.util.SnackBarUtil.defaultSnackBar

class OmokGameActivity : AppCompatActivity(), Observer {
    private var preColor: CoordinateState = BLACK
    private val omokGameService by lazy {
        OmokGameService(this, intent.getIntExtra(ROOM_ID, -1))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.omok_game_activity)

        val board = Board(omokGameService.readBoard())
        runOmokGame(board)
        synchronizeConsoleView(board)
    }

    private fun synchronizeConsoleView(board: Board) {
        board.registerObserver(OmokView())
    }

    private fun runOmokGame(board: Board) {
        board.registerObserver(this)
        initView(board.state)
        setOmokClickListener(board)
    }

    private fun initView(state: State) {
        findViewById<TextView>(R.id.gameTitleText).text = omokGameService.roomTitle
        findViewById<TextView>(R.id.gameFirstUserNameText).text = omokGameService.firstUserName
        findViewById<TextView>(R.id.gameSecondUserNameText).text = omokGameService.secondUserName
        updateView(state)
    }

    private fun setOmokClickListener(board: Board) {
        val boardTable = findViewById<TableLayout>(R.id.board_table)
        boardTable.loopBoardTable { position: Position, imageView ->
            clickPart(board, position, imageView)
        }
    }

    private fun clickPart(board: Board, position: Position, imageView: ImageView) {
        imageView.setOnClickListener {
            preColor = board.state.getTurn()
            board.next(position)
        }
    }

    override fun update(state: State) {
        when (state) {
            is BlackTurn -> updateInfo(state)
            is WhiteTurn -> updateInfo(state)
            is BlackWin -> win(state)
            is WhiteWin -> win(state)
        }
    }

    private fun updateInfo(state: State) {
        if (preColor == state.getTurn()) {
            defaultSnackBar(findViewById(R.id.root), "놓을 수 없는 수 입니다")
            return
        }
        updateView(state)
        omokGameService.updateDb(state, preColor)
    }

    private fun updateView(state: State) {
        initDescription(state.getTurn())
        drawStone(state)
    }

    private fun initDescription(turn: CoordinateState) {
        val gameDescriptionText = findViewById<TextView>(R.id.gameDescriptionText)
        gameDescriptionText.text = DESCRIPTION.format(turn.toColor(), turn.toName(), turn.toColor())
    }

    private fun drawStone(state: State) {
        val boardTable = findViewById<TableLayout>(R.id.board_table)
        boardTable.loopBoardTable { position: Position, imageView ->
            val resId = OmokGameUtil.matchColor(state.stones[position.y, position.x])
            if (resId != null) imageView.setImageResource(resId)
        }
    }

    private fun win(state: State) {
        longToast("${state.getTurn().toName()}의 승리입니다!!!")
        omokGameService.updateDb(state, preColor)
        goToResultActivity(state.getTurn())
    }

    private fun goToResultActivity(turn: CoordinateState) {
        val intent = ResultActivity.getIntent(
            this@OmokGameActivity,
            omokGameService.roomId,
            omokGameService.roomTitle,
            turn.toName(),
        )
        startActivity(intent)
        finish()
    }

    private fun CoordinateState.toColor(): String {
        return if (this == BLACK) "흑" else "백"
    }

    private fun CoordinateState.toName(): String {
        return if (this == BLACK) omokGameService.firstUserName else omokGameService.firstUserName
    }

    override fun onDestroy() {
        omokGameService.closeDb()
        super.onDestroy()
    }

    companion object {
        const val DESCRIPTION = "%s의 차례입니다. %s가 %s입니다"

        fun getIntent(context: Context, roomId: Int) =
            Intent(context, OmokGameActivity::class.java)
                .apply { putExtra(ROOM_ID, roomId) }
    }
}
