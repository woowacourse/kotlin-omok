package woowacourse.omok

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.library.RenjuRule
import omok.model.BoardCoordinate
import omok.model.BoardPosition
import omok.model.OmokGame
import omok.model.OmokStone

class MainActivity : AppCompatActivity() {
    private lateinit var omokGame: OmokGame
    private lateinit var boardViews: Array<Array<ImageView?>>

    var positions = listOf<BoardPosition>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        omokGame = OmokGame(RenjuRule)
        initializeBoard()

    }

    private fun initializeBoard() {
        val board = findViewById<TableLayout>(R.id.board)
        boardViews = Array(15) { arrayOfNulls<ImageView>(15) }

        board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, viewGroup ->
            viewGroup.children.filterIsInstance<ImageView>()
                .forEachIndexed { columnIndex, imageView ->
                    boardViews[rowIndex][columnIndex] = imageView
                    imageView.setOnClickListener {
                        handleStonePlacement(rowIndex, columnIndex, it)
                    }
                }
        }
    }

    private fun handleStonePlacement(row: Int, col: Int, view: View) {
        if (!omokGame.isRunning()) return

        val position = BoardPosition(BoardCoordinate(row), BoardCoordinate(col))
        if (omokGame.placeStoneOnBoard(omokGame.generateNextOmokStone(position))) {

            updateSingleCell(view, omokGame.getCurrentStone()!!)
            updateForbiddenPositions()
        }
    }


    private fun updateForbiddenPositions() {
        positions.filter { it !in omokGame.getForbiddenPositions() }.forEach { forbiddenPosition ->
            val row = forbiddenPosition.getRow()
            val col = forbiddenPosition.getColumn()
            val imageView = boardViews[row][col]

            imageView?.apply {
                setImageResource(0)
            }
        }
        positions = omokGame.getForbiddenPositions()
        positions.forEach { forbiddenPosition ->
            val row = forbiddenPosition.getRow()
            val col = forbiddenPosition.getColumn()
            val imageView = boardViews[row][col]

            imageView?.apply {
                setImageResource(R.drawable.x)
            }
        }
    }


    private fun updateSingleCell(view: View, stone: OmokStone) {
        (view as ImageView).setImageResource(
            when {
                stone.isWhite -> R.drawable.white_stone
                stone.isBlack -> R.drawable.black_stone
                else -> 0 // Clear the image or set to default
            }
        )
    }
}

