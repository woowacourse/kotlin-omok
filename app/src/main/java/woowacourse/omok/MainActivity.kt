package woowacourse.omok

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
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
        getSharedPreferences("Omok", MODE_PRIVATE).getString("nickname", "닉네임") ?: ""
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
        val cursor = getPrevBoardCursor()
        while (cursor.moveToNext()) {
            val positionNumber = cursor.getInt(cursor.getColumnIndexOrThrow(BoardContract.COLUMN_POSITION))
            val stoneId = cursor.getInt(cursor.getColumnIndexOrThrow(BoardContract.COLUMN_STONE))
            stoneCounts[stoneId]++
            positions[getPosition(positionNumber)] = getStone(stoneId)
            setStoneImage(views.elementAt(positionNumber), stoneId)
        }
        cursor.close()
        setPositionViewsListener(views, Board(positions), getTurn(stoneCounts))
    }

    private fun getPrevBoardCursor(): Cursor {
        return omokDB.query(
            BoardContract.TABLE_NAME,
            arrayOf(BoardContract.COLUMN_POSITION, BoardContract.COLUMN_STONE),
            "${BoardContract.COLUMN_NICKNAME} = ?",
            arrayOf(nickname),
            null,
            null,
            "${BoardContract.COLUMN_POSITION} ASC"
        )
    }

    private fun getStone(stoneId: Int): Stone {
        if (stoneId == Black.id) return Black
        return White
    }

    private fun setStoneImage(view: ImageView, stoneId: Int) {
        when (stoneId) {
            Black.id -> view.setImageResource(R.drawable.black_stone)
            White.id -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun setPositionViewsListener(views: Sequence<ImageView>, board: Board, turn: Turn) {
        views.forEachIndexed { index, view ->
            view.setOnClickListener(takeTurn(index, board, turn, view))
        }
    }

    private fun getTurn(counts: List<Int>): Turn {
        if (counts[Black.id] <= counts[White.id]) return Turn(setOf(Black, White), Black.id)
        return Turn(setOf(Black, White), White.id)
    }

    private fun takeTurn(positionNumber: Int, board: Board, turn: Turn, view: ImageView) = OnClickListener {
        val position = getPosition(positionNumber)
        Log.d("test_position", position.toString())
        Log.d("test_turn", turn.now.javaClass.simpleName.toString())
        val result = placeStone(board, turn, position)
        result
            .onSuccess {
                addBoardValue(positionNumber, turn)
                setStoneImage(view, turn.now.id)
                judgeWinner(board, turn, position)
                turn.changeTurn()
            }
            .onFailure { error: Throwable -> showAlertDialog(error.message ?: "") }
    }

    private fun getPosition(index: Int): Position {
        val columnAxis = index % 15
        val rowAxis = 15 - (index / 15) - 1
        return Position(columnAxis, rowAxis)
    }

    private fun placeStone(board: Board, turn: Turn, position: Position): Result<Unit> {
        return runCatching {
            board.placeStone(position, turn.now, referee = BlackReferee())
        }
    }

    private fun addBoardValue(positionNumber: Int, turn: Turn) {
        val values = ContentValues()
        values.put(BoardContract.COLUMN_NICKNAME, nickname)
        values.put(BoardContract.COLUMN_POSITION, positionNumber)
        values.put(BoardContract.COLUMN_STONE, turn.now.id)

        omokDB.insert(BoardContract.TABLE_NAME, null, values)
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
            deletePlayer()
            val intent = Intent(this, WinnerActivity::class.java)
            intent.putExtra("winner", turn.now.toString())
            startActivity(intent)
            finish()
        }
    }

    private fun deletePlayer() {
        val condition = "${PlayerContract.COLUMN_NICKNAME} = ?"
        omokDB.delete(PlayerContract.TABLE_NAME, condition, arrayOf(nickname))
    }

    override fun onDestroy() {
        super.onDestroy()
        omokDB.close()
    }
}
