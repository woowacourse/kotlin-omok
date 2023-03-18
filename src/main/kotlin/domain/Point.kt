package domain

data class Point(private val xCoordinate: XCoordinate, private val yCoordinate: YCoordinate) {

    val x = xCoordinate.x
    val y = yCoordinate.y

    constructor(x: Char, y: Int) : this(XCoordinate.of(x), YCoordinate.of(y))

    fun addX(value: Int): Point = Point(x + value, y)
    fun addY(value: Int): Point = Point(x, y + value)
}