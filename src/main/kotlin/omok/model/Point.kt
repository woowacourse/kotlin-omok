package omok.model

class Point private constructor(val x: Int, val y: Int) {
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
            return cachedPointMap[x to y] ?: error("$x $y 는 ($MIN, $MIN) ~ ($MAX, $MAX) 사이여야 합니다.")
        }
    }
}
