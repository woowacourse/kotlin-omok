package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import omok.model.domain.gameState.GameState
import omok.model.entity.Stone
import omok.model.entity.board.DefaultBoard
import omok.model.entity.position.DefaultPosition
import omok.model.entity.position.Position
import woowacourse.omok.data.History
import woowacourse.omok.data.OmokHistory
import woowacourse.omok.data.OmokHistoryDbHelper
import woowacourse.omok.data.SQLiteOmokHistoryStorage
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private var state: GameState = GameState(board = DefaultBoard())
    private lateinit var omokHistory: OmokHistory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        omokHistory = OmokHistory(SQLiteOmokHistoryStorage(OmokHistoryDbHelper(this)))
        val positions: Sequence<ImageView> = positions()
        loadOmokHistory(positions)
        setOnClickBoardPositions(positions)
    }

    override fun onDestroy() {
        omokHistory.close()
        super.onDestroy()
    }

    private fun positions(): Sequence<ImageView> {
        val boardView: TableLayout = findViewById(R.id.board)
        val positions: Sequence<ImageView> =
            boardView
                .children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
        return positions
    }

    private fun loadOmokHistory(positions: Sequence<ImageView>) {
        thread {
            val histories: List<History> = omokHistory.fetch()
            loadGameState(histories)
            applyOnUi(histories, positions)
        }
    }

    private fun loadGameState(histories: List<History>) {
        histories.forEach { history: History ->
            val position: Position = DefaultPosition(history.row, history.column)
            state = state.play(position)
        }
    }

    private fun applyOnUi(
        histories: List<History>,
        positions: Sequence<ImageView>,
    ) {
        runOnUiThread {
            histories.forEach { history: History ->
                val view = positions.elementAt(positionIndexOf(history.row, history.column))
                val stone: Stone = Stone.valueOf(history.turn)
                view.setImageResource(stone.drawable)
            }
        }
    }

    private fun positionIndexOf(
        row: Int,
        column: Int,
    ): Int = row * 15 + column

    private fun setOnClickBoardPositions(positions: Sequence<ImageView>) {
        positions.forEachIndexed { index: Int, view: ImageView ->
            setOnClickBoardPosition(view, index)
        }
    }

    private fun setOnClickBoardPosition(
        view: ImageView,
        index: Int,
    ) {
        view.setOnClickListener {
            val currentStone: Stone = state.stone
            val position: Position = index.toPosition()
            state = state.play(position)
            view.setImageResource(currentStone.drawable)
            thread {
                omokHistory.add(History(currentStone, position.row, position.column))
            }
            if (!state.playing) {
                thread {
                    omokHistory.clear()
                }
                showResult(currentStone)
            }
        }
    }

    private fun Int.toPosition(): Position {
        val row = this / 15
        val column = this % 15
        return DefaultPosition(row, column)
    }

    private fun showResult(winner: Stone) {
        AlertDialog
            .Builder(this)
            .setTitle("게임 종료")
            .setMessage("승자는 ${winner.prettyString} 입니다.")
            .setPositiveButton("게임 종료") { _, _ ->
                finish()
            }.setNegativeButton("재시작") { _, _ ->
                startActivity(Intent(this, this::class.java))
                finish()
            }.create()
            .show()
    }

    private val Stone.prettyString: String
        get() =
            when (this) {
                Stone.BLACK -> "흑돌"
                Stone.WHITE -> "백돌"
            }

    private val Stone.drawable: Int
        @DrawableRes
        get() =
            when (this) {
                Stone.BLACK -> R.drawable.black_stone
                Stone.WHITE -> R.drawable.white_stone
            }
}
