package woowacourse.omok.view

import android.widget.ImageView
import domain.State
import woowacourse.omok.R

class BoardView(private val board: List<List<ImageView>>) {
    fun setImageViewResource(state: State, row: Int, column: Int) {
        when (state) {
            State.BLACK -> board[row][column].setImageResource(R.drawable.black_stone)
            State.WHITE -> board[row][column].setImageResource(R.drawable.white_stone)
            State.EMPTY -> null
        }
    }
}
