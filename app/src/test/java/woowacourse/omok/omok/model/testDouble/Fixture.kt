package omok.model.testDouble

import omok.model.entity.Stone
import omok.model.entity.board.DefaultBoard
import omok.model.entity.position.DefaultPosition

val EMPTY_BOARD = DefaultBoard()

val BLACK_WIN_BOARD_IF_PUT_1_5 =
    DefaultBoard().apply {
        put(1, 1, Stone.BLACK)
        put(1, 2, Stone.BLACK)
        put(1, 3, Stone.BLACK)
        put(1, 4, Stone.BLACK)
    }

val WHITE_WIN_BOARD_IF_PUT_1_0 =
    DefaultBoard().apply {
        put(1, 1, Stone.WHITE)
        put(1, 2, Stone.WHITE)
        put(1, 3, Stone.WHITE)
        put(1, 4, Stone.WHITE)
    }

val BLACK_FORBIDDEN_BOARD_IF_PUT_1_1 =
    DefaultBoard().apply {
        put(1, 2, Stone.BLACK)
        put(1, 3, Stone.BLACK)
        put(2, 1, Stone.BLACK)
        put(3, 1, Stone.BLACK)
    }

val POSITION_1_5 = DefaultPosition(1, 5)

val POSITION_1_0 = DefaultPosition(1, 0)

val POSITION_1_1 = DefaultPosition(1, 1)
