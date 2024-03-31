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
import woowacourse.omok.model.entity.StoneColor
import woowacourse.omok.model.turn.BlackTurn
import woowacourse.omok.model.turn.Finished
import woowacourse.omok.model.turn.Turn
import woowacourse.omok.view.output.AndroidOutputView
import woowacourse.omok.view.output.OutputView

var currentTurn: Turn = BlackTurn(Board())

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = OmokDbHelper(this)
        val db = dbHelper.writableDatabase
        val dao = OmokDAO(db)

        val board = findViewById<TableLayout>(R.id.board)
        val outputView: OutputView = AndroidOutputView(board)

        printStartGuide(outputView)
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    val x = index % 15 + 1
                    val y = 15 - (index / 15)
                    val point = Point(x, y)

                    val previousTurn: Turn = currentTurn
                    currentTurn =
                        currentTurn.placeStone(
                            point,
                            outputView::printInAppropriatePlace,
                        )

                    if (previousTurn.board == currentTurn.board) {
                        return@setOnClickListener
                    }

                    if (previousTurn.color() == StoneColor.BLACK) {
                        dao.insert(x, y, StoneColor.BLACK.name)
                        view.setImageResource(R.drawable.black_stone)
                    } else {
                        dao.insert(x, y, StoneColor.WHITE.name)
                        view.setImageResource(R.drawable.white_stone)
                    }

                    if (currentTurn is Finished) {
                        outputView.printWinner(currentTurn.board)
                    }
                }
            }
    }

    private fun printStartGuide(outputView: OutputView) {
        outputView.printStartGuide()
    }
}
