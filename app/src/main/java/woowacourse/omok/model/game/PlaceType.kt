package woowacourse.omok.model.game

import woowacourse.omok.model.board.Stone

enum class PlaceType(val stone: Stone) {
    BLACK_PLACE(Stone.BLACK),
    WHITE_PLACE(Stone.WHITE),
    CANNOT_PLACE(Stone.NONE),
}
