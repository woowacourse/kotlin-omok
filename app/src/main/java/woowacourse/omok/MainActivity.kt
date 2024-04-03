package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.model.OmokGame
import woowacourse.omok.model.db.OmokDAO
import woowacourse.omok.model.db.OmokDbHelper
import woowacourse.omok.model.entity.Point
import woowacourse.omok.model.entity.Stone
import woowacourse.omok.model.entity.StoneColor
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
        var omokGame = OmokGame.of(stones.lastOrNull()?.stoneColor, stones)

        printStartGuide(outputView)

        loadCoordinates(board)
            .forEachIndexed { index, view ->
                val point = Point.findPoint(index)
                loadStone(stones, point, view)

                view.setOnClickListener {
                    omokGame.proceedTurn(
                        { point.x to point.y },
                        outputView::printAlert,
                    )

                    if (omokGame.isSameTurn()) {
                        return@setOnClickListener
                    }

                    placeStone(omokGame.placeStoneColor, view)
                    saveStone(omokGame.placeStoneColor, dao, point)

                    if (omokGame.isFinished()) {
                        outputView.printWinner(omokGame.placeStoneColor?.name)
                        loadCoordinates(board).forEach { it.setImageResource(0) }
                        dao.deleteAll()
                        omokGame = OmokGame.of(StoneColor.BLACK)
                    }
                }
            }
    }

    private fun loadCoordinates(board: TableLayout) = board.children
        .filterIsInstance<TableRow>()
        .flatMap { it.children }
        .filterIsInstance<ImageView>()

    private fun printStartGuide(outputView: OutputView) {
        outputView.printAlert("오목 게임을 시작합니다")
    }

    private fun loadStone(
        stones: Set<Stone>,
        point: Point,
        view: ImageView,
    ) {
        stones.find { it.point == point }?.let {
            placeStone(it.stoneColor, view)
        }
    }

    private fun saveStone(
        stoneColor: StoneColor?,
        dao: OmokDAO,
        point: Point,
    ) {
        if (stoneColor == StoneColor.BLACK) {
            dao.insertStone(point.x, point.y, StoneColor.BLACK.name)
        } else {
            dao.insertStone(point.x, point.y, StoneColor.WHITE.name)
        }
    }

    private fun placeStone(
        stoneColor: StoneColor?,
        view: ImageView,
    ) {
        if (stoneColor == StoneColor.BLACK) {
            view.setImageResource(R.drawable.black_stone)
        } else if (stoneColor == StoneColor.WHITE) {
            view.setImageResource(R.drawable.white_stone)
        }
    }
}
