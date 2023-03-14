package domain

data class Stone(private val point: Point) {

    constructor(xCoordinate: XCoordinate, yCoordinate: YCoordinate) : this(Point(xCoordinate, yCoordinate))
}
