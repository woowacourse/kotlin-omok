package woowacourse.omok.domain.model.position

@JvmInline
value class Column private constructor(val value: Int) {
    companion object {
        fun from(
            value: Int,
            maxSize: Int,
        ): Column {
            require(value <= maxSize) { "잘못된 위치입니다." }
            return Column(value)
        }
    }
}
