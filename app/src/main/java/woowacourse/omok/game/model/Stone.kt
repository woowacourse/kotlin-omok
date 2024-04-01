package omok.model

data class Stone(val point: Point, val color: Color) {
    constructor(row: Int, col: Int, color: Color) :
        this(Point(row, col), color)
}
