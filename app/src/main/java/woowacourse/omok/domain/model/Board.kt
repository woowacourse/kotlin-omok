package woowacourse.omok.domain.model

class Board(
    val size: Int = DEFAULT_BOARD_SIZE,
) {
    fun inRange(value: Int) = value in 1..size

    companion object {
        private const val DEFAULT_BOARD_SIZE = 15
    }
}
