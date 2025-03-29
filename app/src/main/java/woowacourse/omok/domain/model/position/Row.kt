package woowacourse.omok.domain.model.position

@JvmInline
value class Row private constructor(val value: Int) {
    companion object {
        fun from(
            value: Int,
            maxSize: Int,
        ): Row {
            require(value in 1..maxSize) { VALIDATE_RANGE_ERROR }
            return Row(value)
        }

        private const val VALIDATE_RANGE_ERROR = "잘못된 행 위치입니다."
    }
}
