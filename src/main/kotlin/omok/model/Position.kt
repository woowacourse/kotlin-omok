package omok.model

class Position private constructor(val x: Int, val y: Int) {

    operator fun plus(other: Vector): Position = of(x + other.x, y + other.y)

    operator fun minus(other: Vector): Position = of(x - other.x, y - other.y)

    constructor(pair: Pair<Int, Int>) : this(pair.first, pair.second)

    companion object {
        private const val MIN = 1
        private const val MAX = 15
        private val RANGE = MIN..MAX
        private val cachedKeys: List<Pair<Int, Int>> =
            List(MAX) {
                val x = it + 1
                RANGE.map { y -> x to y }
            }.flatten()
        private val cachedPositionMap: Map<Pair<Int, Int>, Position> = cachedKeys.associateWith { Position(it) }

        fun of(
            x: Int,
            y: Int,
        ): Position {
            return cachedPositionMap[x to y] ?: Position(x, y)
        }
    }
}
