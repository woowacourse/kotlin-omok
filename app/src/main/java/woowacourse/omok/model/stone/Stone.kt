package woowacourse.omok.model.stone

data class Stone(
    val point: Point,
    val color: StoneColor,
) {
    constructor(row: Int, col: Int, color: StoneColor) : this(Point(row, col), color)
}
