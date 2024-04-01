package woowacourse.omok

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.model.OmokGameState
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import woowacourse.omok.db.StoneDao

class MainActivity : AppCompatActivity() {
    private val stoneDao by lazy { StoneDao(applicationContext) }
    lateinit var omokGameState: OmokGameState

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        omokGameState = loadOmokGameState()
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
                updateOmokUI(omokGameState, boardImageViewMap)
            }
        }
        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
            resetOmokGameState()
            resetOmokUI(boardImageViewMap)
        }
        updateOmokUI(omokGameState, boardImageViewMap)
    }

    private fun loadOmokGameState(): OmokGameState {
        val stones = stoneDao.findAll()
        val points = stones.map { it.point }
        return OmokGameState().runMultiple(points)
    }

    private fun updateOmokState(point: Point) {
        if (omokGameState.isFinished()) {
            return
        }
        stoneDao.save(Stone(point, omokGameState.turn.color()))
        omokGameState = omokGameState.run(point)
        if (omokGameState.isFinished()) {
            displayWinner(omokGameState)
        }
    }

    private fun resetOmokGameState() {
        stoneDao.drop()
        omokGameState = OmokGameState()
    }

    private fun updateOmokUI(
        omokGameState: OmokGameState,
        boardImageViewMap: Map<Point, ImageView>,
    ) {
        omokGameState.turn.board.stones.forEach {
            boardImageViewMap[it.point]?.setImageResource(it.stoneColor.toDrawableId())
        }
    }

    private fun resetOmokUI(
        boardImageViewMap: Map<Point, ImageView>,
    ) {
        boardImageViewMap.forEach{
            it.value.setImageResource(0)
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
