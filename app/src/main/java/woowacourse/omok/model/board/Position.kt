package woowacourse.omok.model.board

data class Position(
    val x: X,
    val y: Y,
) {
    constructor(x: Int, y: Int) : this(X(x), Y(y))
}
