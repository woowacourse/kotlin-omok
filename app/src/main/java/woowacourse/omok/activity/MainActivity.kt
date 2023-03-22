package woowacourse.omok.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.game.*
import domain.point.Point
import domain.rule.BlackRenjuRule
import domain.rule.WhiteRenjuRule
import domain.stone.StoneColor
import view.mapper.toPresentation
import woowacourse.omok.R

class MainActivity : AppCompatActivity() {
    private lateinit var board: TableLayout
    private lateinit var omok: Omok

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showMessage(getString(R.string.omok_start_message))
        omok = Omok(BlackRenjuRule(), WhiteRenjuRule())

        board = findViewById(R.id.board)
        setOmokClickListener()
    }

    private fun setOmokClickListener() {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                val row: Int = index / Omok.OMOK_BOARD_SIZE
                val col: Int = index % Omok.OMOK_BOARD_SIZE

                view.setOnClickListener {
                    val putResult = omok.put { Point(row, col) }
                    processPutResult(putResult, view)
                }
            }
    }

    private fun processPutResult(result: PutResult, view: ImageView) {
        when (result) {
            is PutSuccess -> drawStoneOnBoard(view, result.stoneColor)
            is PutFailed -> showMessage(getString(R.string.inplace_stone))
            is GameFinish -> showWinner(view, result.lastStoneColor, result.winnerStoneColor)
        }
    }

    private fun drawStoneOnBoard(view: ImageView, stoneColor: StoneColor) {
        when (stoneColor) {
            StoneColor.BLACK -> view.setImageResource(R.drawable.black_stone)
            StoneColor.WHITE -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun showWinner(view: ImageView, lastStoneColor: StoneColor, winnerColor: StoneColor) {
        drawStoneOnBoard(view, lastStoneColor)
        showMessage("${winnerColor.toPresentation().text} 승리!")
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
