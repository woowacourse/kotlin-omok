package woowacourse.omok

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.model.game.Board
import omok.model.game.OmokGame
import omok.model.game.PlacementState
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor

class MainActivity : AppCompatActivity() {
    private val omokGame = OmokGame(Board())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        val winner = findViewById<TextView>(R.id.winner_text)
        val retryButton = findViewById<Button>(R.id.retry_button)

        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    val clickedCoordinate: Coordinate = index.toCoordinate()
                    val lastPlacedStone = omokGame.turn(coordinate = { clickedCoordinate })
                    val placedStoneState = omokGame.judge(lastPlacedStone)
                    if (placedStoneState == PlacementState.STAY) {
                        placeGoStoneOnBoard(lastPlacedStone, view)
                        view.isClickable = false
                        return@setOnClickListener
                    }
                    placeGoStoneOnBoard(lastPlacedStone, view)
                    disableBoard(board)
                    setWinnerText(winner, lastPlacedStone.color, placedStoneState)
                }
            }

        retryButton.setOnClickListener {
            recreate()
        }
    }

    private fun placeGoStoneOnBoard(lastPlacedStone: GoStone, view: ImageView) {
        if (lastPlacedStone.color == GoStoneColor.BLACK) view.setImageResource(R.drawable.black_stone)
        else view.setImageResource(R.drawable.white_stone)
    }

    private fun disableBoard(board: TableLayout) {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { it.isClickable = false }
    }

    private fun setWinnerText(
        winnerTextView: TextView,
        color: GoStoneColor,
        placementState: PlacementState
    ) {
        winnerTextView.text = when (placementState) {
            PlacementState.WIN -> "${color.name} 승리!"
            else -> "금수!: ${placementState.name}, ${GoStoneColor.WHITE.name} 승리!"
        }
    }

    private fun Int.toCoordinate(): Coordinate {
        val index = this + 1
        val x = if (index % BOARD_SIZE == 0) BOARD_SIZE else index % BOARD_SIZE
        val y = if (index % BOARD_SIZE == 0) BOARD_SIZE - index / BOARD_SIZE + 1 else BOARD_SIZE - index / BOARD_SIZE
        return Coordinate(x, y)
    }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
