package omok.model

@JvmInline
value class Line(val value: Int) {
    init {
        require(value in 1..15)
    }
}
