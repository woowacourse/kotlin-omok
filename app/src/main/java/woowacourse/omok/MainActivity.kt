package woowacourse.omok

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.domain.Turn
import omok.domain.board.Board
import omok.domain.board.Position
import omok.domain.getTurn
import omok.domain.judgment.BlackReferee
import omok.domain.judgment.ResultReferee
import omok.domain.player.Black
import omok.domain.player.Stone
import omok.domain.player.White
import woowacourse.omok.db.BoardContract
import woowacourse.omok.db.OmokDBHelper
import woowacourse.omok.db.PlayerContract

class MainActivity : AppCompatActivity() {
    private val omokDB: SQLiteDatabase by lazy { OmokDBHelper(this).writableDatabase }
    private val nickname: String by lazy {
        Omok.sharedPref.getString("nickname", "닉네임") ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nicknameView = findViewById<TextView>(R.id.nickname)
        nicknameView.text = nickname

        val board = findViewById<TableLayout>(R.id.board)
        val positionViews = board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()

        setBoard(positionViews)
    }

    private fun setBoard(views: Sequence<ImageView>) {
        val positions: MutableMap<Position, Stone?> = Board.POSITIONS.associateWith { null }.toMutableMap()
        val stoneCounts = mutableListOf(0, 0)
        val cursor = BoardContract.readRecords(omokDB, nickname)
        while (cursor.moveToNext()) {
            val positionNumber = cursor.getInt(cursor.getColumnIndexOrThrow(BoardContract.COLUMN_NAME_POSITION))
            val stoneId = cursor.getInt(cursor.getColumnIndexOrThrow(BoardContract.COLUMN_NAME_STONE))
            stoneCounts[stoneId]++
            positions[Position.of(positionNumber)] = Stone.of(stoneId)
            setStoneImage(views.elementAt(positionNumber), stoneId)
        }
        cursor.close()

        val turn = getTurn(stoneCounts)
        renderTurn(turn)
        setPositionViewsListener(views, Board(positions), turn)
    }

    private fun setStoneImage(view: ImageView, stoneId: Int) {
        when (stoneId) {
            Black.id -> view.setImageResource(R.drawable.black_stone)
            White.id -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun renderTurn(turn: Turn) {
        when (turn.now) {
            Black -> {
                findViewById<ImageView>(R.id.black_turn).setImageResource(R.drawable.turn_on)
                findViewById<ImageView>(R.id.white_turn).setImageResource(R.drawable.turn_off)
            }
            White -> {
                findViewById<ImageView>(R.id.white_turn).setImageResource(R.drawable.turn_on)
                findViewById<ImageView>(R.id.black_turn).setImageResource(R.drawable.turn_off)
            }
        }
    }

    private fun setPositionViewsListener(views: Sequence<ImageView>, board: Board, turn: Turn) {
        views.forEachIndexed { index, view ->
            view.setOnClickListener(takeTurn(index, board, turn, view))
        }
    }

    private fun takeTurn(positionNumber: Int, board: Board, turn: Turn, view: ImageView) = OnClickListener {
        val position = Position.of(positionNumber)
        Log.d("test_position", position.toString())
        Log.d("test_turn", turn.now.javaClass.simpleName.toString())
        val result = placeStone(board, turn, position)
        result
            .onSuccess {
                BoardContract.createRecord(omokDB, nickname, positionNumber, turn.now.id)
                setStoneImage(view, turn.now.id)
                judgeWinner(board, turn, position)
                changeTurn(turn)
            }
            .onFailure { error: Throwable -> showAlertDialog(error.message ?: "") }
    }

    private fun placeStone(board: Board, turn: Turn, position: Position): Result<Unit> {
        return runCatching {
            board.placeStone(position, turn.now, referee = BlackReferee())
        }
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.error_24)
        builder.setTitle("돌을 놓을 수 없습니다!")
        builder.setMessage(message)
        builder.setPositiveButton("확인", null)
        builder.show()
    }

    private fun judgeWinner(board: Board, turn: Turn, position: Position) {
        if (ResultReferee().checkWinner(board.positions, position)) {
            PlayerContract.deleteRecord(omokDB, nickname)
            val intent = Intent(this, WinnerActivity::class.java)
            intent.putExtra("winner", turn.now.toString())
            startActivity(intent)
            finish()
        }
    }

    private fun changeTurn(turn: Turn) {
        turn.changeTurn()
        renderTurn(turn)
    }

    override fun onDestroy() {
        super.onDestroy()
        omokDB.close()
    }
}
