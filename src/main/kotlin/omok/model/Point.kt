package omok.model

class Point private constructor(val x: Int, val y: Int) {

    init {
        require(x >= MIN) { "x는 $MIN 보다 이상이야 한다" }
        require(y >= MIN) { "y는 $MIN 보다 이상이야 한다" }
    }

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
        private val cachedPointMap: Map<Pair<Int, Int>, Point> = cachedKeys.associateWith { Point(it) }

        fun of(
            x: Int,
            y: Int,
        ): Point {
            return cachedPointMap[x to y] ?: Point(x, y)
        }
    }
}
