package domain

data class Stone(private val xCoordinate: XCoordinate, private val yCoordinate: YCoordinate) {

    val x = xCoordinate.x
    val y = yCoordinate.y
    constructor(x: Char, y: Int) : this(XCoordinate.of(x), YCoordinate.of(y))
}
