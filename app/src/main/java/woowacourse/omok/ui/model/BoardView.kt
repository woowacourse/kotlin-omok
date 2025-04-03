package woowacourse.omok.ui.model

import android.widget.ImageView

class BoardView(private val value: List<List<ImageView>>) {
    val size get() = value.size

    fun updateBoard(update: (Int, Int, ImageView) -> Unit) {
        value.forEachIndexed { row, tableRow ->
            tableRow.forEachIndexed { column, view ->
                update(row, column, view)
            }
        }
    }
}
