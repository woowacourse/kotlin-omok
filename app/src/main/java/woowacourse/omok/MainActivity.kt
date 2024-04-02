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
                val point = Point.findPoint(index)

                loadOmokBoard(stones, point, view)

                view.setOnClickListener {
                    val previousTurn: Turn = currentTurn
                    currentTurn =
                        currentTurn.placeStone(
                            point,
                            outputView::printAlert,
                        )

                    if (previousTurn.board == currentTurn.board) {
                        return@setOnClickListener
                    }

                    placeStone(previousTurn.color(), view)
                    saveStone(previousTurn, dao, point)

                    if (currentTurn is Finished) {
                        outputView.printWinner(currentTurn.board.lastStoneColor())

                        board.children
                            .filterIsInstance<TableRow>()
                            .flatMap { it.children }
                            .filterIsInstance<ImageView>()
                            .forEach {
                                it.setImageResource(0)
                            }
                        dao.deleteAll()
                        currentTurn = BlackTurn(Board())
                    }
                }
            }
    }

    private fun printStartGuide(outputView: OutputView) {
        outputView.printAlert("오목 게임을 시작합니다")
    }

    private fun loadOmokBoard(
        stones: Set<Stone>,
        point: Point,
        view: ImageView,
    ) {
        stones.find { it.point == point }?.let {
            placeStone(it.stoneColor, view)
        }
    }

    private fun saveStone(
        previousTurn: Turn,
        dao: OmokDAO,
        point: Point,
    ) {
        if (previousTurn.color() == StoneColor.BLACK) {
            dao.insertStone(point.x, point.y, StoneColor.BLACK.name)
        } else {
            dao.insertStone(point.x, point.y, StoneColor.WHITE.name)
        }
    }

    private fun placeStone(
        stoneColor: StoneColor,
        view: ImageView,
    ) {
        if (stoneColor == StoneColor.BLACK) {
            view.setImageResource(R.drawable.black_stone)
        } else if (stoneColor == StoneColor.WHITE) {
            view.setImageResource(R.drawable.white_stone)
        }
    }
}
