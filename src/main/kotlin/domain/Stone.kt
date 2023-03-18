package domain

data class Stone(val point: Point) {
    constructor(x: Char, y: Int) : this(Point(x, y))
    constructor(xCoordinate: XCoordinate, yCoordinate: YCoordinate) : this(xCoordinate.x, yCoordinate.y)
}
