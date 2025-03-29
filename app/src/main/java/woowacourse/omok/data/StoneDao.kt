package woowacourse.omok.data

import woowacourse.omok.domain.position.Col
import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.position.Row
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

class StoneDao(
    private val _stoneColor: String,
    private val _row: Int,
    private val _col: Int,
) {
    val stoneColor get() = _stoneColor.toStoneColor()
    val row get() = _row.toRow()
    val col get() = _col.toCol()

    fun toStone(): Stone {
        return Stone(Position(row, col), stoneColor)
    }

    companion object {
        fun valueOf(stone: Stone): StoneDao {
            return StoneDao(
                stone.color.name,
                stone.position.row.value,
                stone.position.col.value,
            )
        }

        private fun String.toStoneColor(): StoneColor =
            if (this == StoneColor.BLACK.name) {
                StoneColor.BLACK
            } else {
                StoneColor.WHITE
            }

        private fun Int.toRow(): Row = Row.from(this)

        private fun Int.toCol(): Col = Col.from(this)
    }
}
