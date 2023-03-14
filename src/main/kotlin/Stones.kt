abstract class Stones(list: List<Stone>) {
    private val _list = list.toList()
    val list: List<Stone>
        get() = _list.toList()

    val isWin: Boolean
        get() = checkWin()

    constructor(vararg stone: Stone) : this(stone.toList())

    abstract fun putStone(stone: Stone): Stones

    abstract fun getColor(): Color

    fun getLatestStone(): Point = _list.last().point

    fun isPossiblePut(point: Point): Boolean = list.any { stone -> stone.point == point }.not()

    private fun checkWin(): Boolean {
        val N = 15
        val dx = intArrayOf(1, 1, 0, -1)
        val dy = intArrayOf(0, 1, 1, 1)

        for (i in 1..N) {
            for (j in 1..N) {
                if (list.contains(Stone(Point(i, j))).not()) continue

                for (k in 0 until 4) {
                    var cnt = 1
                    var nx = i + dx[k]
                    var ny = j + dy[k]

                    while (nx in 1..N && ny in 1..N && list.contains(Stone(Point(nx, ny)))) {
                        cnt++
                        nx += dx[k]
                        ny += dy[k]

                        if (cnt >= 5) return true
                    }
                }
            }
        }
        return false
    }
}
