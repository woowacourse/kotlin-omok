package woowacourse.omok.model.board

object OmokBoardConfig {
    const val X_MAX_RANGE = 15
    const val Y_MAX_RANGE = 15
    const val X_MIN_RANGE = 1
    const val Y_MIN_RANGE = 1

    const val MIN_X = 0
    const val MAX_X = X_MAX_RANGE - X_MIN_RANGE
    const val MIN_Y = 0
    const val MAX_Y = Y_MAX_RANGE - Y_MIN_RANGE

    const val INDEX_OFFSET = 1

    const val RIGHT = 1
    const val UP = -1
    const val DOWN = 1
    const val STILL = 0
    const val ZERO = 0
    const val MAX_BLINK_COUNT = 1

    const val FOUR_STONE_PATTERN = 4
    const val TWO_STONE_PATTERN = 2
    const val WINNING_LINE_LENGTH = 5
    const val NEXT_POSITION = 1

    const val CANNOT_PLACE_STONE = 0
    const val CAN_PLACE_STONE = 1

    const val NO_EMPTY_SPACE = 0
    const val WHITE_STONE_WIN_CONDITION = 4

    @JvmStatic
    val X_Edge = listOf(MIN_X, MAX_X)

    @JvmStatic
    val Y_Edge = listOf(MIN_Y, MAX_Y)
}
