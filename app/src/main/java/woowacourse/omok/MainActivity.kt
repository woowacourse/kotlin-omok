package woowacourse.omok

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.state.State
import domain.state.end.End
import domain.state.running.BlackTurn
import domain.state.running.Running
import domain.state.running.WhiteTurn
import domain.stone.Board
import domain.stone.StonePosition
import view.OutputView
import woowacourse.omok.database.StonePositionConstract
import woowacourse.omok.database.StonePositionDbHelper

class MainActivity : AppCompatActivity() {

    private val board: TableLayout by lazy { findViewById<TableLayout>(R.id.board) }
    private val retryButton: Button by lazy { findViewById(R.id.retry_btn) }
    private lateinit var boardViews: List<List<ImageView>>

    private val db: SQLiteDatabase by lazy { StonePositionDbHelper(this).writableDatabase }
    private var state: State = BlackTurn()
    private val boardMap: Board = Board()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boardViews = getBoardViews()

        updateBoardViewFromDb()
        setBoardClickEvent()
        setRetryButtonClickEvent()
    }

    private fun getBoardViews(): List<List<ImageView>> =
        List(board.childCount) { row ->
            val tableRow = board.getChildAt(row) as TableRow
            List(tableRow.childCount) { col ->
                tableRow.getChildAt(col) as ImageView
            }
        }

    private fun setBoardClickEvent() {
        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { row, it ->
                it.children.filterIsInstance<ImageView>()
                    .forEachIndexed { col, view ->
                        view.setOnClickListener { putStone(row, col, view) }
                    }
            }
    }

    private fun putStone(row: Int, col: Int, view: ImageView) {
        val stonePosition: StonePosition = StonePosition(col + 1, row + 1)
        if (!state.isEnd() && !(state as Running).isPlaced(boardMap, stonePosition)) {
            view.setImageResource(getStoneImage(state))
            state = state.next(boardMap, stonePosition)
            saveStoneToDb(row, col)
        }
        if (state.isEnd()) {
            retryButton.visibility = View.VISIBLE
            Toast.makeText(
                this,
                "${OutputView().getWinnerText(state as End)}돌이 승리하였습니다.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun saveStoneToDb(row: Int, col: Int) {
        val values = ContentValues().apply {
            put(StonePositionConstract.TABLE_COLUMN_ROW, row)
            put(StonePositionConstract.TABLE_COLUMN_COLUMN, col)
        }

        db.insert(StonePositionConstract.TABLE_NAME, null, values)
    }

    private fun updateBoardViewFromDb() {
        val cursor = db.query(
            StonePositionConstract.TABLE_NAME,
            arrayOf(
                StonePositionConstract.TABLE_COLUMN_ROW,
                StonePositionConstract.TABLE_COLUMN_COLUMN
            ),
            "", arrayOf(), null, null, ""
        )

        with(cursor) {
            while (moveToNext()) {
                val positionRow: Int =
                    getInt(getColumnIndexOrThrow(StonePositionConstract.TABLE_COLUMN_ROW))
                val positionCol: Int =
                    getInt(getColumnIndexOrThrow(StonePositionConstract.TABLE_COLUMN_COLUMN))
                putStone(positionRow, positionCol, boardViews[positionRow][positionCol])
            }
        }

        cursor.close()
    }

    private fun setRetryButtonClickEvent() {
        retryButton.setOnClickListener {
            db.delete(StonePositionConstract.TABLE_NAME, "", arrayOf())
        }
    }

    private fun getStoneImage(state: State): Int {
        return when (state) {
            is BlackTurn -> R.drawable.stone_black_pokemon_ball_luxury
            is WhiteTurn -> R.drawable.stone_white_pokemon_ball_premier
            else -> 0
        }
    }
}
