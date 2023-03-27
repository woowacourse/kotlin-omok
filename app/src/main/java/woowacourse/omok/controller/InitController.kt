package woowacourse.omok.controller

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import omok.domain.BlackStone
import omok.domain.Board
import omok.domain.WhiteStone
import omok.domain.state.Turn
import woowacourse.omok.MainActivity
import woowacourse.omok.R
import woowacourse.omok.database.DBController

class InitController(
    private val omokBoard: Board,
    private val board: TableLayout,
    private val dbController: DBController,
) {
    fun initBoard() {
        listOf(Turn.Black.color, Turn.White.color).forEach { color ->
            val indexes = dbController.getIndex(color)
            indexes.forEach { index -> initDisplay(color, index) }
        }
    }

    private fun initDisplay(color: String, index: Int) {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { itIndex, view ->
                if (itIndex == index) {
                    initPut(index, color, view)
                }
            }
    }

    private fun initPut(index: Int, color: String, view: ImageView) {
        val position = MainActivity.positionFind(index)
        if (color == Turn.Black.color) {
            view.setImageResource(R.drawable.black_stone)
            omokBoard.blackPlayer.put(BlackStone(position))
        }
        if (color == Turn.White.color) {
            view.setImageResource(R.drawable.white_stone)
            omokBoard.whitePlayer.put(WhiteStone(position))
        }
        omokBoard.occupyPosition(position)
    }
}
