package woowacourse.omok

import android.os.Bundle
import android.util.Log
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
import woowacourse.omok.database.stoneposition.StonePositionConstract
import woowacourse.omok.database.stoneposition.StonePositionDbHandler
import woowacourse.omok.database.stoneposition.StonePositionDbHelper

class MainActivity : AppCompatActivity() {

    private val board: TableLayout by lazy { findViewById<TableLayout>(R.id.board) }
    private val retryButton: Button by lazy { findViewById(R.id.retry_btn) }
    private val boardViews: List<List<ImageView>> by lazy { createBoardViews() }

    private val dbHandler: StonePositionDbHandler by lazy {
        StonePositionDbHandler(StonePositionDbHelper(this).writableDatabase)
    }
    private var state: State = BlackTurn()
    private val boardMap: Board = Board()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateBoardViewFromDb()
        setBoardClickEvent()
        setRetryButtonClickEvent()
    }

    private fun createBoardViews(): List<List<ImageView>> {
        return List(board.childCount) { row ->
            val tableRow = board.getChildAt(row) as TableRow
            List(tableRow.childCount) { col ->
                tableRow.getChildAt(col) as ImageView
            }
        }
    }

    private fun setBoardClickEvent() {
        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { row, it ->
                it.children.filterIsInstance<ImageView>()
                    .forEachIndexed { col, view ->
                        view.setOnClickListener {
                            putStone(StonePosition(col + 1, row + 1), view)
                        }
                    }
            }
    }

    private fun putStone(stonePosition: StonePosition, view: ImageView) {
        if (!state.isEnd() && !(state as Running).isPlaced(boardMap, stonePosition)) {
            Log.d("stone_position_debug", "row: ${stonePosition.y}, col: ${stonePosition.x}")
            view.setImageResource(getStoneImage(state))
            state = state.next(boardMap, stonePosition)
            dbHandler.addColumn(stonePosition)
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

    private fun updateBoardViewFromDb() {
        val cursor = dbHandler.getCursor()

        with(cursor) {
            while (moveToNext()) {
                val positionRow: Int =
                    getInt(getColumnIndexOrThrow(StonePositionConstract.TABLE_COLUMN_ROW))
                val positionCol: Int =
                    getInt(getColumnIndexOrThrow(StonePositionConstract.TABLE_COLUMN_COLUMN))
                putStone(
                    StonePosition(positionCol, positionRow),
                    boardViews[positionRow - 1][positionCol - 1]
                )
            }
        }

        cursor.close()
    }

    private fun setRetryButtonClickEvent() {
        retryButton.setOnClickListener {
            dbHandler.deleteAllColumns()
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
