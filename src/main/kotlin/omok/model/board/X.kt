package omok.model.board

@JvmInline
value class X(
    val point: Int,
) {
    init {
        require(point in 1..15) { "X좌표의 범위는 A부터 O까지 입니다." }
    }
}
