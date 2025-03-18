package omok

@JvmInline
value class Line(val value: Int) {
    init {
        require(value in 0..14)
    }
}
