package omok.model

@JvmInline
value class Y(val point: Int) {
    init {
        require(point in 1..15) { "Y좌표는 1에서 15까지이다." }
    }
}
