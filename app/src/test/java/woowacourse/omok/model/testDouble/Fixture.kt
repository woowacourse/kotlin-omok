package woowacourse.omok.model.testDouble

import woowacourse.omok.model.Stone
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.position.Position

val EMPTY_BOARD = Board()

val BLACK_WIN_BOARD_IF_PUT_1_5 =
    Board().apply {
        put(1, 1, Stone.BLACK)
        put(1, 2, Stone.BLACK)
        put(1, 3, Stone.BLACK)
        put(1, 4, Stone.BLACK)
    }

val WHITE_WIN_BOARD_IF_PUT_1_0 =
    Board().apply {
        put(1, 1, Stone.WHITE)
        put(1, 2, Stone.WHITE)
        put(1, 3, Stone.WHITE)
        put(1, 4, Stone.WHITE)
    }

val BLACK_FORBIDDEN_BOARD_IF_PUT_1_1 =
    Board().apply {
        put(1, 2, Stone.BLACK)
        put(1, 3, Stone.BLACK)
        put(2, 1, Stone.BLACK)
        put(3, 1, Stone.BLACK)
    }

val BLACK_EXIST_STONE_IF_PUT_1_3 =
    Board().apply {
        put(1, 2, Stone.BLACK)
        put(1, 3, Stone.BLACK)
    }

val POSITION_1_5 = Position(1, 5)

val POSITION_1_0 = Position(1, 0)

val POSITION_1_1 = Position(1, 1)

val POSITION_5_5 = Position(5, 5)
