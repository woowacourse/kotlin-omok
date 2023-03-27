package woowacourse.omok.omokgame

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import domain.CoordinateState
import domain.CoordinateState.BLACK
import domain.CoordinateState.EMPTY
import domain.CoordinateState.WHITE
import domain.Position
import woowacourse.omok.R

object OmokGameUtil {

    fun matchColor(color: CoordinateState): Int? {
        return when (color) {
            BLACK -> R.drawable.black_stone
            WHITE -> R.drawable.white_stone
            EMPTY -> null
        }
    }

    fun TableLayout.loopBoardTable(action: (Position, ImageView) -> Unit) {
        children
            .filterIsInstance<TableRow>()
            .forEachIndexed { y, tableRow -> tableRow.loopRow(y, action) }
    }

    private fun TableRow.loopRow(y: Int, action: (Position, ImageView) -> Unit) {
        children
            .filterIsInstance<ImageView>()
            .forEachIndexed { x, view -> action(Position(y, x), view) }
    }
}
