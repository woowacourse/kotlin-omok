package woowacourse.omok.kotlin.omok.model

import woowacourse.omok.src.main.kotlin.omok.model.board.Board
import woowacourse.omok.src.main.kotlin.omok.model.stone.StoneType

const val BOARD_SIZE = 15

fun resetBoard() {
    repeat(BOARD_SIZE) { row ->
        repeat(BOARD_SIZE) { col ->
            Board.putStone(row, col, StoneType.NONE)
        }
    }
}

fun resetPosition(
    target: Any,
    fieldName: String,
) {
    val field = target.javaClass.getDeclaredField(fieldName)

    with(field) {
        isAccessible = true
        set(target, null)
    }
}
