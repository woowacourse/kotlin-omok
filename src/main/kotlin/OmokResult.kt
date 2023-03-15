enum class OmokResult {
    RUNNING,
    FIVE_STONE_WINNING;

    // THREE_TO_THREE,
    // FOUR_TO_FOUR,
    // LONG_STONES;

    companion object {
        private const val N = 15
        fun valueOf(stones: List<Stone>, color: Color): OmokResult {
            return when (checkWin(stones, color)) {
                true -> FIVE_STONE_WINNING
                false -> RUNNING
            }
        }

        private fun checkWin(placedStones: List<Stone>, color: Color): Boolean {
            val dx = intArrayOf(1, 1, 0, -1)
            val dy = intArrayOf(0, 1, 1, 1)

            for (i in 1..N) {
                for (j in 1..N) {
                    if (placedStones.contains(Stone(Point(i, j), color)).not()) continue

                    for (k in 0 until 4) {
                        var cnt = 1
                        var nx = i + dx[k]
                        var ny = j + dy[k]

                        while (nx in 1..N && ny in 1..N && placedStones.contains(Stone(Point(nx, ny), color))) {
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
}
