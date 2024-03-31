package woowacourse.omok.omokdb

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import omok.model.position.Position
import omok.model.stone.BlackStone
import omok.model.stone.GoStone
import omok.model.stone.WhiteStone

object OmokRestoreData {
    private const val BLACK_STONE_VALUE = "흑"

    fun restoreGameData(
        dao: OmokDao,
        board: TableLayout,
        imageView: (GoStone) -> Int,
    ) {
        dao.findAll().forEach { omokEntry ->
            recoverBoard(
                board,
                Position.of(omokEntry.row[0], omokEntry.column),
                omokEntry.color,
                imageView,
            )
        }
    }

    private fun recoverBoard(
        board: TableLayout,
        position: Position,
        stoneColor: String,
        imageView: (GoStone) -> Int,
    ) {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                if (Position.fromIndex(index) == position) {
                    recoverStone(stoneColor.stone(), position, view, imageView)
                }
            }
    }

    private fun recoverStone(
        stone: GoStone,
        position: Position,
        view: ImageView,
        imageView: (GoStone) -> Int,
    ) {
        view.setImageResource(imageView(stone))
        stone.putStone(position)
    }

    private fun String.stone() = if (this == BLACK_STONE_VALUE) BlackStone else WhiteStone
}
