package domain

data class Coordinate private constructor(override val x: Int, override val y: Int) : Point(x, y) {
    constructor(point: Point): this(point.x, point.y)
    override fun plus(other: Point): Coordinate {
        return Coordinate(super.plus(other))
    }
    companion object {
        fun from(x: Int, y: Int): Coordinate? {
            if (x > 15 || y > 15 || x < 1 || y < 1)
                return null
            return Coordinate(x, y)
        }
    }
}
