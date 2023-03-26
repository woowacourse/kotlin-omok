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
    private var gameBoard = Board(Stones())
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
        var currentColor = if (stones.isEmpty()) Color.BLACK else stones.last().color.turnColor()
        val viewBoard = findViewById<TableLayout>(R.id.board)
        viewBoard
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                val x = index % 15
                val y = index / 15
                gameBoard.stones.value.find {
                    it.coordinate.x == x && it.coordinate.y == y
                }?.let {
                    setStoneDrawable(it.color, view)
                }
                view.setOnClickListener {
                    val stone = Stone(currentColor, Coordinate.from(x, y))
                    if (gameBoard.processTurn(
                            stone
                        )
                    ) {
                        setStoneDrawable(currentColor, view)
                        checkWin(currentColor)
                        dbHelper.writeOmokStone(stone)
                        currentColor = currentColor.turnColor()
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
        }
    }

    private fun setStoneDrawable(currentColor: Color, view: ImageView) {
        when (currentColor) {
            Color.BLACK -> view.setImageDrawable(getDrawable(R.drawable.black_stone))
            Color.WHITE -> view.setImageDrawable(getDrawable(R.drawable.white_stone))
        }
    }
}