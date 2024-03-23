package omok.model

import omok.rule.Rule

data class Point(val x: Int, val y: Int) {
    init {
        require(x in MIN_POINT until Rule.BOARD_SIZE && y in MIN_POINT until Rule.BOARD_SIZE)
    }

    companion object {
        private const val MIN_POINT = 0
    }
}
