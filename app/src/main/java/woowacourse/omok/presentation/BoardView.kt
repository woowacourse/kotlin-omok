package woowacourse.omok.presentation

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children

abstract class BoardView(private val value: TableLayout) {
    fun setBoardTask() {
        value
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, tableRow ->
                setCoordinateTask(
                    tableRow,
                    rowIndex
                )
            }
    }

    private fun setCoordinateTask(
        tableRow: TableRow,
        rowIndex: Int
    ) = tableRow.children
        .filterIsInstance<ImageView>()
        .forEachIndexed() { columIndex, imageView ->
            boardTask(imageView = imageView, columIndex = columIndex, rowIndex = rowIndex)
        }

    abstract fun boardTask(imageView: ImageView, rowIndex: Int, columIndex: Int)
}
