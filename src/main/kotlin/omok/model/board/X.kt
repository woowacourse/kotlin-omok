package omok.model.board

@JvmInline
value class X(
    val point: Int,
) {
    init {
        require(point in 1..15) { "좌표의 범위는 1부터 15까지 입니다." }
    }
}
