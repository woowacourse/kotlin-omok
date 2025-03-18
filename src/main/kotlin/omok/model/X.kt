package omok.model

@JvmInline
value class X(
    val point: String,
) {
    init {
        require(point in "A".."O") { "X좌표의 범위는 A부터 O까지 입니다." }
    }
}
