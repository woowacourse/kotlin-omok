package woowacourse.omok.view

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import woowacourse.omok.model.android.BlockAndroidModel

class BoardView(tableLayout: TableLayout) {
    private var boardView: List<List<ImageView>>

    init {
        boardView = tableLayout.children
            .filterIsInstance<TableRow>()
            .map { row -> row.children.filterIsInstance<ImageView>().toList() }
            .toList()
    }

    fun updateBlock(block: BlockAndroidModel) {
        val (x, y, state) = block
        boardView[x][y].setImageResource(state.res)
    }

    fun reset() {
        boardView.applyOnChildren { imageView ->
            imageView.setImageResource(INITIAL_RESOURCE)
        }
    }

    fun disableClickListener() {
        boardView.applyOnChildren { imageView ->
            imageView.setOnClickListener(null)
        }
    }

    fun setBoardClickListener(clickListener: BlockClickListener) {
        boardView.applyOnChildren { imageView, x, y ->
            imageView.setOnClickListener { clickListener.onClick(x + 1, y + 1) }
        }
    }

    private fun List<List<ImageView>>.applyOnChildren(action: (ImageView, Int, Int) -> Unit) {
        boardView.forEachIndexed { x, row ->
            row.forEachIndexed { y, imageView ->
                action(imageView, x, y)
            }
        }
    }

    private fun List<List<ImageView>>.applyOnChildren(action: (ImageView) -> Unit) {
        boardView.forEach { row ->
            row.forEach { imageView ->
                action(imageView)
            }
        }
    }


    fun interface BlockClickListener {
        fun onClick(x: Int, y: Int)
    }

    companion object {
        private const val INITIAL_RESOURCE = 0
    }
}