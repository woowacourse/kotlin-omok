package omok.domain.model.position

@JvmInline
value class Row(val value: Int) {
    init {
        require(value in RANGE) { "잘못된 위치입니다." }
    }

    companion object {
        private val RANGE = 1..15
    }
}
