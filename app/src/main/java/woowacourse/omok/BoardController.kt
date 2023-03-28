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
import woowacourse.omok.db.OmokDB

class BoardController(
    value: TableLayout,
    private val omokGame: OmokGame,
    private val db: OmokDB
) {
    private val boardView: List<List<ImageView>> =
        value.children
            .filterIsInstance<TableRow>()
            .map { it.children.filterIsInstance<ImageView>().toList() }.toList()

    init {
        omokGame.gameState.omokBoard.value
            .asSequence()
            .filter { it.value != EmptyStoneState }
            .forEach {
                placeStoneView(it.key, it.value)
            }

        setPlaceStoneEventListener()
    }

    private fun placeStoneView(omokPoint: OmokPoint, stoneState: StoneState) {
        boardView.forEachIndexed { row, images ->
            images.forEachIndexed { col, view ->
                if (omokPoint == OmokPoint(row + 1, col + 1)) {
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

    private fun determineStoneColor(stoneState: StoneState): Int {
        return when (stoneState) {
            BlackStoneState -> 0
            else -> 1
        }
    }

    private fun setPlaceStoneEventListener() {
        boardView.forEachIndexed { row, images ->
            images.forEachIndexed { col, view ->
                view.setOnClickListener {
                    if (omokGame.gameState.isRunning) {
                        val point = OmokPoint(row + 1, col + 1)
                        val state = omokGame.play(point)
                        if (omokGame.gameState === state) {
                            return@setOnClickListener
                        }
                        db.recordStoneInfo(point, determineStoneColor(state.stoneState))
                        placeStoneView(point, state.stoneState)
                    }
                }
            }
        }
    }
}
