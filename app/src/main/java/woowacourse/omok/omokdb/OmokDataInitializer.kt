package woowacourse.omok.omokdb

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import omok.model.board.Board

object OmokDataInitializer {
    private const val RESET_IMAGE_ID = 0

    fun resetGameData(
        dao: OmokDao,
        board: TableLayout,
    ) {
        resetBoardImage(board)
        Board.resetBoard()
        dao.reset()
    }

    private fun resetBoardImage(board: TableLayout) {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                view.setImageResource(RESET_IMAGE_ID)
            }
    }
}
