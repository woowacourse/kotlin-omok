package woowacourse.omok

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import domain.CoordinateState
import domain.CoordinateState.BLACK
import domain.CoordinateState.WHITE
import domain.Position

object OmokGameUtil {
    private const val BLACK_TEXT = "흑"
    private const val WHITE_TEXT = "백"

    fun CoordinateState.toName(): String {
        return if (this == BLACK) BLACK_TEXT else WHITE_TEXT
    }

    fun matchColor(color: CoordinateState): Int? {
        return when (color) {
            BLACK -> R.drawable.black_stone
            WHITE -> R.drawable.white_stone
            else -> null
        }
    }

    fun loopBoardTable(boardTable: TableLayout, action: (Position, ImageView) -> Unit) {
        boardTable.children
            .filterIsInstance<TableRow>()
            .forEachIndexed { y, tableRow -> loopRow(tableRow, y, action) }
    }

    private fun loopRow(tableRow: TableRow, y: Int, action: (Position, ImageView) -> Unit) {
        tableRow.children
            .filterIsInstance<ImageView>()
            .forEachIndexed { x, view -> action(Position(y, x), view) }
    }
}
