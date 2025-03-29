package omok.domain

import woowacourse.omok.domain.StoneType

class Turn {
    var color: StoneType = StoneType.BLACK
        private set

    fun next() {
        color =
            if (color == StoneType.BLACK) {
                StoneType.WHITE
            } else {
                StoneType.BLACK
            }
    }

    fun isWhite(): Boolean = color == StoneType.WHITE

    fun reset() {
        color = StoneType.BLACK
    }
}
