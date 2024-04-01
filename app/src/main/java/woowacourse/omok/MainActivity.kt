package woowacourse.omok

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.controller.OmokController
import woowacourse.omok.model.Color
import woowacourse.omok.model.GameEventListener

class MainActivity : AppCompatActivity(), GameEventListener {
    private lateinit var boardLayout: TableLayout
    private lateinit var omokController: OmokController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boardLayout = findViewById(R.id.board)
        omokController = OmokController()
        omokController.gameEventListener = this
        omokController.start()

        boardLayout
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                view.setOnClickListener {
                    if (!omokController.gameEnded)
                        handleStonePlacement(view)
                }
            }

        val restartButton: Button = findViewById(R.id.restartButton)
        restartButton.setOnClickListener {
            omokController.restartGame()
            resetBoardView()
        }
    }

    override fun onForbiddenStone() {
        viewToastMessage(FORBIDDEN_STONE_MESSAGE, SHORT_DURATION)
    }

    override fun onGameEnd(winner: Color) {
        runOnUiThread {
            viewToastMessage(WINNER_MESSAGE.format(getPlayerColor(winner)), LONG_DURATION)
        }
    }

    private fun handleStonePlacement(clickedView: ImageView) {
        val tableRow = clickedView.parent as? ViewGroup
        val col = tableRow?.indexOfChild(clickedView)
        val row = tableRow?.let { tblRow -> boardLayout.indexOfChild(tblRow) }

        if (col != null && row != null) {
            Log.d("MainActivity", "x: ${col}, y: $row")
            tryPlaceStone(row + INDEX_ADJUSTMENT, col + INDEX_ADJUSTMENT, clickedView)
        }
    }

    private fun tryPlaceStone(
        row: Int,
        col: Int,
        clickedView: ImageView,
    ) {
        val color = omokController.getNextTurn()
        if (omokController.placeStoneAtPosition(row, col)) {
            viewStone(color, clickedView)
        }
    }

    private fun viewStone(
        color: Color,
        view: ImageView,
    ) {
        when (color) {
            Color.BLACK -> view.setImageResource(R.drawable.black_stone)
            Color.WHITE -> view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun resetBoardView() {
        for (r in MIN_INDEX until boardLayout.childCount) {
            val row = boardLayout.getChildAt(r) as TableRow
            for (c in MIN_INDEX until row.childCount) {
                val cell = row.getChildAt(c) as ImageView
                cell.setImageDrawable(null)
            }
        }
    }

    private fun viewToastMessage(message: String?, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }

    private fun getPlayerColor(color: Color): String {
        return when (color) {
            Color.BLACK -> PLAYER_COLOR_BLACK
            Color.WHITE -> PLAYER_COLOR_WHITE
        }
    }

    companion object {
        const val INDEX_ADJUSTMENT = 1
        const val MIN_INDEX = 0
        const val FORBIDDEN_STONE_MESSAGE = "이 위치는 금수입니다!"
        const val WINNER_MESSAGE = "게임 종료! 우승자는 %s 입니다!"
        const val PLAYER_COLOR_BLACK = "흑"
        const val PLAYER_COLOR_WHITE = "백"
        const val SHORT_DURATION = 3
        const val LONG_DURATION = 8
    }
}
