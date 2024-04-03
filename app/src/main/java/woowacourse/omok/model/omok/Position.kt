package woowacourse.omok.model.omok

data class Position(val x: Int, val y: Int) {
    operator fun plus(other: Vector): Position = Position(x + other.position.x, y + other.position.y)

    operator fun minus(other: Vector): Position = Position(x - other.position.x, y - other.position.y)

    constructor(pair: Pair<Int, Int>) : this(pair.first, pair.second)

    override fun toString(): String = "Position ($x, $y)"
}
