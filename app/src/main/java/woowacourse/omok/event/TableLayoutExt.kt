package woowacourse.omok.event

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import omok.domain.board.OmokBoard
import omok.domain.place.Place

fun TableLayout.setEvent(block: ((Int, Int, ImageView) -> Unit)?) {
    val rows =
        this
            .children
            .filterIsInstance<TableRow>()
            .toList()

    rows.forEachIndexed { y, row ->
        val column = row.children.filterIsInstance<ImageView>()
        column.forEachIndexed { x, view ->
            view.setOnClickListener {
                block?.invoke(
                    x,
                    y,
                    view,
                )
            }
        }
    }
}

fun TableLayout.removeAllEvent() {
    this.setEvent(null)
}

fun OmokBoard.getPointAt(
    x: Int,
    y: Int,
): Place {
    return this.omokStones.getPointAt(OmokBoard.MAX_ROW_SIZE - y, x + 1)
}
