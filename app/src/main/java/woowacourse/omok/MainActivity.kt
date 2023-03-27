package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.*

class MainActivity : AppCompatActivity() {
    private lateinit var gameBoard: Board
    private val dbHelper = OmokGameDbHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val stones = dbHelper.readOmokBoardData()
        gameBoard = Board(Stones(stones))

        setOmokBoard(stones)
    }

    private fun setOmokBoard(stones: List<Stone>) {
        gameBoard.currentColor =
            if (stones.isEmpty()) Color.BLACK else stones.last().color.turnColor()
        val viewBoard = findViewById<TableLayout>(R.id.board)
        viewBoard
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                val x = index % 15
                val y = index / 15
                gameBoard.stones.findStone(x,y)?.let {
                    view.setStoneDrawable(it.color)
                }
                view.setOnClickListener {
                    val stone = Stone(gameBoard.currentColor, Coordinate.from(x, y))
                    if (gameBoard.processTurn(
                            stone
                        )
                    ) {
                        view.setStoneDrawable(gameBoard.currentColor)
                        checkWin(gameBoard.currentColor)
                        dbHelper.writeOmokStone(stone)
                    }
                }
            }
    }

    private fun checkWin(currentColor: Color) {
        if (gameBoard.isWinPlace()) {
            Toast.makeText(
                this,
                "${currentColor} 승",
                Toast.LENGTH_SHORT
            ).show()
            restartGame()
        }
    }

    private fun restartGame() {
        dbHelper.clearBoard()
        gameBoard.restartGame()
        recreate()
    }

    private fun ImageView.setStoneDrawable(currentColor: Color) {
        when (currentColor) {
            Color.BLACK -> setImageDrawable(getDrawable(R.drawable.black_stone))
            Color.WHITE -> setImageDrawable(getDrawable(R.drawable.white_stone))
        }
    }
}