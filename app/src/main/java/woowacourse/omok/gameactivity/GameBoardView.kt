package woowacourse.omok.gameactivity

import android.widget.ImageView
import android.widget.TableLayout
import woowacourse.omok.BoardView
import woowacourse.omok.util.setOnSingleClickListener

class GameBoardView(
    value: TableLayout,
    private val coordinateClickListener: (ImageView, Int, Int) -> Unit
) : BoardView(value) {

    init {
        setBoardTask()
    }

    override fun boardTask(imageView: ImageView, rowIndex: Int, columIndex: Int) {
        imageView.setOnSingleClickListener {
            coordinateClickListener(imageView, rowIndex, columIndex)
        }
    }
}
