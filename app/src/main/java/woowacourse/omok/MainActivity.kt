package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.db.OmokDAO
import woowacourse.omok.model.db.OmokDbHelper
import woowacourse.omok.model.entity.Point
import woowacourse.omok.model.entity.Stone
import woowacourse.omok.model.entity.StoneColor
import woowacourse.omok.model.turn.BlackTurn
import woowacourse.omok.model.turn.Finished
import woowacourse.omok.model.turn.Turn
import woowacourse.omok.model.turn.WhiteTurn
import woowacourse.omok.view.output.AndroidOutputView
import woowacourse.omok.view.output.OutputView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = OmokDbHelper(this)
        val db = dbHelper.writableDatabase
        val dao = OmokDAO(db)
        val board = findViewById<TableLayout>(R.id.board)
        val outputView: OutputView = AndroidOutputView(board)
        val stones = dao.selectAll()
        var currentTurn: Turn =
            when (stones.lastOrNull()?.stoneColor) {
                StoneColor.BLACK -> WhiteTurn(Board(stones))
                StoneColor.WHITE -> BlackTurn(Board(stones))
                else -> BlackTurn(Board())
            }

        printStartGuide(outputView)

        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                val point = findPoint(index)

                initOmokBoard(stones, point, view)

                view.setOnClickListener {
                    val previousTurn: Turn = currentTurn
                    currentTurn =
                        currentTurn.placeStone(
                            point,
                            outputView::printInAppropriatePlace,
                        )

                    if (previousTurn.board == currentTurn.board) {
                        return@setOnClickListener
                    }

                    saveStone(previousTurn, dao, point, view)
                    removeAllGameStatus(currentTurn, outputView, board, dao)
                }
            }
    }

    private fun printStartGuide(outputView: OutputView) {
        outputView.printStartGuide()
    }

    private fun findPoint(index: Int): Point {
        val x = index % 15 + 1
        val y = 15 - (index / 15)

        return Point(x, y)
    }

    private fun initOmokBoard(
        stones: Set<Stone>,
        point: Point,
        view: ImageView,
    ) {
        stones.find { it.point == point }.let {
            if (it?.stoneColor == StoneColor.BLACK) {
                view.setImageResource(R.drawable.black_stone)
            } else if (it?.stoneColor == StoneColor.WHITE) {
                view.setImageResource(R.drawable.white_stone)
            }
        }
    }

    private fun saveStone(
        previousTurn: Turn,
        dao: OmokDAO,
        point: Point,
        view: ImageView,
    ) {
        if (previousTurn.color() == StoneColor.BLACK) {
            dao.insert(point.x, point.y, StoneColor.BLACK.name)
            view.setImageResource(R.drawable.black_stone)
        } else {
            dao.insert(point.x, point.y, StoneColor.WHITE.name)
            view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun removeAllGameStatus(
        currentTurn: Turn,
        outputView: OutputView,
        board: TableLayout,
        dao: OmokDAO,
    ) {
        if (currentTurn is Finished) {
            outputView.printWinner(currentTurn.board)
            board.removeAllViews()
            dao.deleteAll()
        }
    }
}
