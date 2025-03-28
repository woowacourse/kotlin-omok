package woowacourse.omok.view.ext

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import omok.domain.board.OmokBoard
import omok.domain.place.Place

fun TableLayout.setView(block: (Int, Int, ImageView) -> Unit) {
    val rows =
        this
            .children
            .filterIsInstance<TableRow>()
            .toList()

    rows.forEachIndexed { y, row ->
        val column = row.children.filterIsInstance<ImageView>()
        column.forEachIndexed { x, view ->
            block(
                x + 1,
                OmokBoard.MAX_ROW_SIZE - y,
                view,
            )
        }
    }
}

fun TableLayout.setOnClickListener(block: (Int, Int, ImageView) -> Unit) {
    setView { x, y, view ->
        view.setOnClickListener {
            block(x, y, view)
        }
    }
}

fun OmokBoard.getPointAt(
    x: Int,
    y: Int,
): Place {
    return omokStones.getPointAt(y, x)
}
