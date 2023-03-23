package domain.stone

import domain.Direction

data class Point(private val xCoordinate: XCoordinate, private val yCoordinate: YCoordinate) {
    val x: Char = xCoordinate.x
    val y: Int = yCoordinate.y

    constructor(x: Char, y: Int) : this(XCoordinate.of(x), YCoordinate.of(y))

    constructor(x: Int, y: Int) : this(XCoordinate.of(x), YCoordinate.of(y))

    infix fun goTo(direction: Direction): Point = Point(x + direction.dx, y + direction.dy)
}
