package domain.model

@JvmInline
value class Row(val value: Int) {
    init {
        require(value in 1..15) { "잘못된 위치입니다." }
    }
}
