package woowacourse.omok.model

data class Position(
    val horizontalCoordinate: Int,
    val verticalCoordinate: Int,
) {
    constructor(flattenedIndex: Int) : this(
        horizontalCoordinate = BOARD_DISPLAY_SIZE - flattenedIndex / BOARD_DISPLAY_SIZE,
        verticalCoordinate = flattenedIndex % BOARD_DISPLAY_SIZE + 1,
    )

    companion object {
        private const val BOARD_DISPLAY_SIZE = 15
    }
}
