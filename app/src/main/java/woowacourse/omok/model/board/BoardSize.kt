package woowacourse.omok.model.board

@JvmInline
value class BoardSize(
    val value: Int,
) {
    init {
        require(value in BOARD_MIN_SIZE..BOARD_MAX_SIZE) { " 규격 내의 보드판을 생성해주세요." }
    }

    companion object {
        const val BOARD_MIN_SIZE = 1
        const val OMOK_BOARD_SIZE = 15
        private const val BOARD_MAX_SIZE: Int = 25
    }
}
