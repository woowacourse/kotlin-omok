package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.OmokResult
import woowacourse.omok.domain.StoneColor
import woowacourse.omok.domain.grid.Column
import woowacourse.omok.domain.grid.OmokGrid
import woowacourse.omok.domain.grid.OmokGrid.Companion.DEFAULT_SIZE
import woowacourse.omok.domain.grid.OmokPoint
import woowacourse.omok.domain.grid.Point
import woowacourse.omok.domain.grid.Row

class MainActivity : AppCompatActivity() {
    private val omokGame = OmokGame(OmokGrid())
    private var isGameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        var nowTurn = omokGame.getStartingPlayer()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()
            .forEachIndexed { index, view ->
                val row = index / DEFAULT_SIZE
                val col = index % DEFAULT_SIZE
                view.tag = OmokPoint(Point(Row(row + INDEX_OFFSET), Column(col + INDEX_OFFSET)), nowTurn)

                view.setOnClickListener {
                    if (isGameOver) return@setOnClickListener
                    nowTurn = proceedTurn(view, nowTurn)
                }
            }
    }

    private fun proceedTurn(
        view: ImageView,
        stoneColor: StoneColor,
    ): StoneColor {
        val point = view.tag as OmokPoint
        if (isViolation(stoneColor, point)) return stoneColor
        playMove(stoneColor, view)
        checkGameOver(stoneColor, point)
        return omokGame.changeTurn(stoneColor)
    }

    private fun isViolation(
        stoneColor: StoneColor,
        point: OmokPoint,
    ): Boolean {
        kotlin.runCatching {
            omokGame.validatePoint(stoneColor, point)
        }.onFailure { e ->
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

    private fun playMove(
        stoneColor: StoneColor,
        view: ImageView,
    ) {
        view.setImageResource(getStoneImage(stoneColor))
        omokGame.playMove(view.tag as OmokPoint)
    }

    private fun getStoneImage(stoneColor: StoneColor): Int {
        return when (stoneColor) {
            StoneColor.BLACK -> R.drawable.black_stone
            StoneColor.WHITE -> R.drawable.white_stone
        }
    }

    private fun checkGameOver(
        stoneColor: StoneColor,
        point: OmokPoint,
    ) {
        when {
            omokGame.checkWin(stoneColor, point) -> {
                printWinner(OmokResult.getWinner(stoneColor))
                isGameOver = true
            }
            omokGame.isBoardFull() -> {
                printWinner(OmokResult.DRAW)
                isGameOver = true
            }
        }
    }

    private fun printWinner(result: OmokResult) {
        Toast.makeText(this, "$result !!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val INDEX_OFFSET: Int = 1
    }
}
