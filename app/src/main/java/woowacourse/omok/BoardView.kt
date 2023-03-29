package woowacourse.omok

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import omok.OmokGame
import omok.OmokPoint
import omok.state.BlackStoneState
import omok.state.EmptyStoneState
import omok.state.StoneState
import omok.state.WhiteStoneState

class BoardView(
    value: TableLayout,
    private val omokGame: OmokGame,
) {

    val boardView: List<List<ImageView>> =
        value.children
            .filterIsInstance<TableRow>()
            .map { it.children.filterIsInstance<ImageView>().toList() }.toList()

    init {
        omokGame.gameState.omokBoard.value
            .asSequence()
            .filter { it.value != EmptyStoneState }
            .forEach {
                onSuccessProcess(it.key, it.value)
            }
    }

    fun onSuccessProcess(point: OmokPoint, stoneState: StoneState) {
        boardView.forEachIndexed { row, images ->
            images.forEachIndexed { col, view ->
                val viewPoint = OmokPoint(row + 1, col + 1)
                if (point == viewPoint) {
                    determineImageView(stoneState, view)
                }
            }
        }
    }

    private fun determineImageView(stoneState: StoneState, view: ImageView) {
        when (stoneState) {
            BlackStoneState -> view.setImageResource(R.drawable.black_stone)
            WhiteStoneState -> view.setImageResource(R.drawable.white_stone)
        }
    }
}
