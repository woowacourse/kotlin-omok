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

            for (i in 0 until N) {
                for (j in 0 until N) {
                    if (placedStones.contains(Stone(Position(i, j), color)).not()) continue

                    for (k in 0 until 4) {
                        var cnt = 1
                        var nx = i + dx[k]
                        var ny = j + dy[k]

                        while (nx in 0 until N && ny in 0 until N && placedStones.contains(Stone(Position(nx, ny), color))) {
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
