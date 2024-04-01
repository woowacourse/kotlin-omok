package woowacourse.omok.model

data class Position(val x: Int, val y: Int) {
    operator fun plus(other: Vector): Position = Position(x + other.x, y + other.y)

    operator fun minus(other: Vector): Position = Position(x - other.x, y - other.y)

    constructor(pair: Pair<Int, Int>) : this(pair.first, pair.second)

    override fun toString(): String = "Position ($x, $y)"
}
