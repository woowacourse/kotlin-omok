package domain.stone

abstract class Stone(val point: Point) {
    constructor(x: Char, y: Int) : this(Point(x, y))
    constructor(xCoordinate: XCoordinate, yCoordinate: YCoordinate) : this(
        xCoordinate.x,
        yCoordinate.y
    )

    override fun equals(other: Any?): Boolean {
        if (other !is Stone) return false
        return other.point == this.point
    }

    override fun hashCode(): Int {
        return 1
    }
}
