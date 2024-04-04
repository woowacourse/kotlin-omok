package woowacourse.omok

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import omok.model.OmokGameState
import omok.model.entity.Point

class OmokBoardImageView(
    boardView: TableLayout,
) : OmokStateListener {
    private val boardImageViewMap: Map<Point, ImageView> =
        boardView
            .children
            .filterIsInstance<TableRow>()
            .mapIndexed { row, tableRow ->
                tableRow.children.filterIsInstance<ImageView>().map { row to it }
            }.flatMap {
                it.mapIndexed { column, (row, imageView) ->
                    Point(column + 1, row + 1) to imageView
                }
            }.toMap()

    fun setOnClickListener(function: (Point) -> Unit) {
        boardImageViewMap.forEach { entry ->
            entry.value.setOnClickListener {
                function(entry.key)
            }
        }
    }

    override fun listen(omokState: OmokGameState) {
        boardImageViewMap.forEach {
            it.value.setImageResource(0)
        }
        omokState.turn.board.stones.forEach {
            boardImageViewMap[it.point]?.setImageResource(it.stoneColor.toDrawableId())
        }
    }
}
