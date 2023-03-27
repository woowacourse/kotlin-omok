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
import woowacourse.omok.data.dao.BoardDao
import woowacourse.omok.data.dao.RoomDao
import woowacourse.omok.data.dao.UserDao
import woowacourse.omok.home.HomeActivity.Companion.ROOM_ID
import woowacourse.omok.home.Room
import woowacourse.omok.omokgame.OmokGameUtil.loopBoardTable
import woowacourse.omok.result.ResultActivity
import woowacourse.omok.util.ContextUtil.longToast
import woowacourse.omok.util.SnackBarUtil.defaultSnackBar

class OmokGameActivity : AppCompatActivity(), Observer {
    private var preColor: CoordinateState = BLACK
    private val boardTable by lazy { findViewById<TableLayout>(R.id.board_table) }
    private val room by lazy { getRoomInfo() }

    private val boardDao = BoardDao(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.omok_game_activity)

        val board = Board(boardDao.readBoard(room.roomId))
        runOmokGame(board)
        synchronizeConsoleView(board)
    }

    private fun getRoomInfo(): Room {
        val roomDao = RoomDao(this)
        val userDao = UserDao(this)

        val roomId = intent.getIntExtra(ROOM_ID, -1)
        val roomEntity = roomDao.getRoom(roomId)
        val room = with(roomEntity) {
            Room(roomId, roomTitle, userDao.getUser(firstUserId), userDao.getUser(secondUserId))
        }

        roomDao.closeDb()
        userDao.closeDb()
        return room
    }

    private fun synchronizeConsoleView(board: Board) {
        board.registerObserver(OmokView())
    }

    private fun runOmokGame(board: Board) {
        initView(board.state)
        board.registerObserver(this)
        setOmokClickListener(board)
    }

    private fun initView(state: State) {
        findViewById<TextView>(R.id.gameTitleText).text = room.roomTitle
        findViewById<TextView>(R.id.gameFirstUserNameText).text = room.firstUserEntity.userName
        findViewById<TextView>(R.id.gameSecondUserNameText).text = room.secondUserEntity.userName
        initDescription(state.getTurn())
        drawStone(state)
    }

    private fun initDescription(turn: CoordinateState) {
        val gameDescriptionText = findViewById<TextView>(R.id.gameDescriptionText)
        gameDescriptionText.text = DESCRIPTION.format(turn.toColor(), turn.toName(), turn.toColor())
    }

    private fun setOmokClickListener(board: Board) {
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
            is BlackTurn -> updateView(state)
            is WhiteTurn -> updateView(state)
            is BlackWin -> win(state)
            is WhiteWin -> win(state)
        }
    }

    private fun updateView(state: State) {
        if (preColor == state.getTurn()) {
            defaultSnackBar(findViewById(R.id.root), "놓을 수 없는 수 입니다")
            return
        }
        initDescription(state.getTurn())
        drawStone(state)
        updateDb(state)
    }

    private fun drawStone(state: State) {
        boardTable.loopBoardTable { position: Position, imageView ->
            val resId = OmokGameUtil.matchColor(state.stones[position.y, position.x])
            if (resId != null) imageView.setImageResource(resId)
        }
    }

    private fun updateDb(state: State) {
        val y = (state.getLastPosition() ?: return).y
        val x = (state.getLastPosition() ?: return).x
        boardDao.insertStone(room.roomId, y, x, preColor)
    }

    private fun win(state: State) {
        boardTable.loopBoardTable { _, imageView -> imageView.isEnabled = false }
        longToast("${state.getTurn().toName()}의 승리입니다!!!")
        drawStone(state)
        updateDb(state)
        goToResultActivity(state.getTurn())
    }

    private fun goToResultActivity(turn: CoordinateState) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra(ROOM_ID, room.roomId)
            putExtra(ROOM_TITLE, room.roomTitle)
            putExtra(WINNER_NAME, turn.toName())
        }
        startActivity(intent)
        finish()
    }

    private fun CoordinateState.toColor(): String {
        return if (this == BLACK) "흑" else "백"
    }

    private fun CoordinateState.toName(): String {
        return if (this == BLACK) room.firstUserEntity.userName else room.secondUserEntity.userName
    }

    override fun onDestroy() {
        boardDao.closeDb()
        super.onDestroy()
    }

    companion object {
        const val WINNER_NAME = "winnerName"
        const val ROOM_TITLE = "roomTitle"
        const val DESCRIPTION = "%s의 차례입니다. %s가 %s입니다"

        fun getIntent(context: Context, roomId: Int) =
            Intent(context, OmokGameActivity::class.java)
                .apply { putExtra(ROOM_ID, roomId) }
    }
}
