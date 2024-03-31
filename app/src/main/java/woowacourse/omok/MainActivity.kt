package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.model.OmokGameState
import omok.model.entity.Point
import omok.model.entity.StoneColor

class MainActivity : AppCompatActivity() {
    var omokGameState = OmokGameState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val board = findViewById<TableLayout>(R.id.board)
        val boardImageViewMap: Map<Point, ImageView> =
            board
                .children
                .filterIsInstance<TableRow>()
                .mapIndexed { row, tableRow ->
                    tableRow.children.filterIsInstance<ImageView>().map { row to it }
                }.flatMap {
                    it.mapIndexed { column, (row, imageView) ->
                        Point(column + 1, row + 1) to imageView
                    }
                }.toMap()
        boardImageViewMap.forEach { entry ->
            entry.value.setOnClickListener {
                updateOmokState(entry.key)
                updateOmokImage(omokGameState, boardImageViewMap)
            }
        }
    }

    private fun updateOmokState(point: Point) {
        if (omokGameState.isFinished()) {
            return
        }
        omokGameState = omokGameState.run(point)
        if (omokGameState.isFinished()) {
            displayWinner(omokGameState)
        }
    }

    private fun updateOmokImage(
        omokGameState: OmokGameState,
        boardImageViewMap: Map<Point, ImageView>,
    ) {
        omokGameState.turn.board.stones.forEach {
            boardImageViewMap[it.point]?.setImageResource(it.stoneColor.toDrawableId())
        }
    }

    private fun displayWinner(omokGameState: OmokGameState) {
        val turn = omokGameState.turn
        val winnerText =
            if (turn.isWin()) {
                getString(turn.color().toStringId()) + getString(R.string.winning_message)
            } else {
                getString(R.string.draw_message)
            }
        Toast.makeText(applicationContext, winnerText, Toast.LENGTH_SHORT).show()
    }
}

fun StoneColor.toDrawableId() =
    when (this) {
        StoneColor.BLACK -> R.drawable.black_stone
        StoneColor.WHITE -> R.drawable.white_stone
    }

fun StoneColor.toStringId() =
    when (this) {
        StoneColor.BLACK -> R.string.black
        StoneColor.WHITE -> R.string.white
    }
