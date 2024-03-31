package omock.model

data class Position(val x: Int, val y: Int) {
    operator fun plus(other: Vector): Position = Position(x + other.x, y + other.y)

    operator fun minus(other: Vector): Position = Position(x - other.x, y - other.y)

    infix operator fun compareTo(other: Position): Int {
        val xCompare = this.x compareTo other.x
        if (xCompare != 0) return xCompare
        return this.y compareTo other.y
    }

    constructor(pair: Pair<Int, Int>) : this(pair.first, pair.second)
}
