package woowacourse.omok.console.model
import domain.game.Omok.Companion.OMOK_BOARD_SIZE

class PointModel(val row: String, val col: String) {
    override fun toString(): String = "$col$row"

    companion object {
        private const val ROW_START = 1
        private const val ROW_END = OMOK_BOARD_SIZE
        val ROW_RANGE: List<String> = (ROW_START..ROW_END).map(Int::toString)

        private const val COLUMN_START: Char = 'A'
        private const val COLUMN_END: Char = 'A' + (OMOK_BOARD_SIZE - 1)
        val COLUMN_RANGE: List<String> = (COLUMN_START..COLUMN_END).map(Char::toString)
    }
}
