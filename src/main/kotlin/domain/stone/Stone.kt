package domain.stone

data class Stone(val point: Point) {

    val x = point.x
    val y = point.y

    constructor(x: Char, y: Int) : this(Point(XCoordinate.of(x), YCoordinate.of(y)))

    constructor(x: Int, y: Int) : this(Point(XCoordinate.of(x), YCoordinate.of(y)))

    constructor(x: XCoordinate, y: YCoordinate) : this(Point(x, y))
}
