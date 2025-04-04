package woowacourse.omok.model.board

data class Position(
    val x: X,
    val y: Y,
) {
    val xPoint = x.point
    val yPoint = y.point

    constructor(x: Int, y: Int) : this(X(x), Y(y))
}
