package domain

data class Stone(val xCoordinate: XCoordinate, val yCoordinate: YCoordinate) {
    constructor(x: Char, y: Int) : this(XCoordinate.of(x), YCoordinate.of(y))
}
