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

    const val EMPTY_STONE = 0
    const val BLACK_STONE = 1
    const val WHITE_STONE = 2

    @JvmStatic
    val X_Edge = listOf(MIN_X, MAX_X)

    @JvmStatic
    val Y_Edge = listOf(MIN_Y, MAX_Y)
}
