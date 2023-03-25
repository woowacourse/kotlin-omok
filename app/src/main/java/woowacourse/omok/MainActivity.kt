package woowacourse.omok

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
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

    private lateinit var board: TableLayout
    private lateinit var db: SQLiteDatabase
    private var state: State = BlackTurn()
    private val boardMap: Board = Board()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = StonePositionDbHelper(this).writableDatabase
        board = findViewById<TableLayout>(R.id.board)

        setBoardClickEvent()
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
            Toast.makeText(
                this,
                "${OutputView().getWinnerText(state as End)}돌이 승리하였습니다.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun saveStoneToDb(row: Int, col: Int) {
        val values = ContentValues().apply {
            put(StonePositionConstract.TABLE_COLUMN_ROW, row + 1)
            put(StonePositionConstract.TABLE_COLUMN_COLUMN, col + 1)
        }

        db.insert(StonePositionConstract.TABLE_NAME, null, values)
    }

    private fun getStoneImage(state: State): Int {
        return when (state) {
            is BlackTurn -> R.drawable.stone_black_pokemon_ball_luxury
            is WhiteTurn -> R.drawable.stone_white_pokemon_ball_premier
            else -> 0
        }
    }
}
