package omok.model.stone

data class Stone(
    val point: Point,
    val color: StoneColor,
) {
    companion object {
        fun of(
            row: Int,
            col: Int,
            color: StoneColor,
        ) = Stone(Point(row, col), color)
    }
}
