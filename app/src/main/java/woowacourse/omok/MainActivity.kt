package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.Board
import domain.rule.FourFourRule
import domain.rule.LongMokRule
import domain.rule.Referee
import domain.rule.ThreeThreeRule

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val omokBoard = Board()
        lateinit var point: Pair<Int, Int>
        val blackReferee = Referee(listOf(ThreeThreeRule(), FourFourRule(), LongMokRule()))


        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    point = calculatePoint(index)
                    var stoneResource: Int? = null
                    if (omokBoard.isBlackTurn()) {
                        stoneResource = R.drawable.black_stone
                    }
                    if (omokBoard.isWhiteTurn()) {
                        stoneResource = R.drawable.white_stone
                    }
                    val result = putStoneAndReturnResult(omokBoard, blackReferee, point)
                    if (result) stoneResource?.let { view.setImageResource(it) }
                }
            }
    }

    private fun calculatePoint(index: Int): Pair<Int, Int> {
        val x: Int = index % 15
        val y: Int = 14 - index / 15
        return Pair(x, y)
    }

    private fun putStoneAndReturnResult(
        board: Board,
        blackReferee: Referee,
        point: Pair<Int, Int>
    ): Boolean {
        runCatching {
            board.put(
                point,
                blackReferee
            )
        }.onFailure {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}
