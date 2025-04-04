package woowacourse.omok.domain.model.position

@JvmInline
value class Column private constructor(val value: Int) {
    companion object {
        fun from(
            value: Int,
            maxSize: Int,
        ): Column {
            require(value in 1..maxSize) { VALIDATE_RANGE_ERROR }
            return Column(value)
        }

        private const val VALIDATE_RANGE_ERROR = "잘못된 열 위치입니다."
    }
}
