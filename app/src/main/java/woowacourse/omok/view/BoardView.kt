package woowacourse.omok.view

import android.widget.ImageView
import domain.OmokBoard
import domain.State
import woowacourse.omok.R

class BoardView(private val board: List<List<ImageView>>) {
    private fun setImageViewResource(state: State, row: Int, column: Int) {
        when (state) {
            State.BLACK -> board[row][column].setImageResource(R.drawable.black_stone)
            State.WHITE -> board[row][column].setImageResource(R.drawable.white_stone)
            State.EMPTY -> null
        }
    }

    fun setImageResources(omokBoard: OmokBoard) {
        omokBoard.value.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, state ->
                setImageViewResource(state, rowIndex, columnIndex)
            }
        }
    }
}
