package domain

data class Point(private val xCoordinate: XCoordinate, private val yCoordinate: YCoordinate) {
    val x: Char = xCoordinate.x
    val y: Int = yCoordinate.y

    constructor(x: Char, y: Int) : this(XCoordinate.of(x), YCoordinate.of(y))
}
