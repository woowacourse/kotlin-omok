package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.database.OmokContract
import woowacourse.omok.database.OmokDbHelper
import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.Game
import woowacourse.omok.model.MoveResult
import woowacourse.omok.model.Stone
import woowacourse.omok.model.position.Col
import woowacourse.omok.model.position.Position
import woowacourse.omok.model.position.Row
import woowacourse.omok.model.rule.RenjuRule
import woowacourse.omok.view.OutputViewAndroid

class MainActivity : AppCompatActivity() {
    private val game = Game(Board(), RenjuRule())
    private val outputView = OutputViewAndroid()
    private val dbHelper = OmokDbHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        val boardLayout: TableLayout = findViewById(R.id.board)
        val views: Sequence<ImageView> =
            boardLayout.children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
        restoreGame(views)
        outputView.printOmokStart(boardLayout)
        setListeners(boardLayout, views)
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    private fun initialize() {
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun restoreGame(views: Sequence<ImageView>) {
        val stones: List<Stone> = dbHelper.queryAll()
        stones.forEach { stone ->
            game.play(stone)
            val index =
                (stone.position.y.value - 1) * game.board.row.value + (stone.position.x.value - 1)
            views.toList()[index].setImageResource(stone.color.toImage())
        }
    }

    private fun setListeners(
        boardLayout: TableLayout,
        views: Sequence<ImageView>,
    ) {
        views.forEachIndexed { index, view ->
            view.setOnClickListener { onClick(index, boardLayout, view, views) }
        }
    }

    private fun onClick(
        index: Int,
        boardLayout: TableLayout,
        view: ImageView,
        views: Sequence<ImageView>,
    ) {
        val color: Color = game.chooseTurn()
        val stoneImage: Int = color.toImage()
        val x = Col(index % game.board.col.value + 1)
        val y = Row(index / game.board.row.value + 1)

        when (val moveResult: MoveResult = game.play(Stone(Position(x, y), color))) {
            is MoveResult.Success.Playing -> {
                view.setImageResource(stoneImage)
                dbHelper.insertData(x, y, color)
            }

            is MoveResult.Success.Finished -> {
                view.setImageResource(stoneImage)
                dbHelper.insertData(x, y, color)
                outputView.printMoveResult(moveResult, this, boardLayout)
                clearListeners(views)
                dbHelper.writableDatabase.delete(OmokContract.TABLE_NAME, null, null)
                return
            }

            is MoveResult.Failure -> {
                outputView.printMoveResult(moveResult, this, boardLayout)
            }
        }
    }

    private fun clearListeners(views: Sequence<ImageView>) {
        views.forEach { view -> view.setOnClickListener(null) }
    }

    private fun Color.toImage(): Int =
        when (this) {
            Color.BLACK -> R.drawable.black_stone
            Color.WHITE -> R.drawable.white_stone
        }
}
