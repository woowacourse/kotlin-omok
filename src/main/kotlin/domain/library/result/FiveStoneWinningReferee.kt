package domain.library.result

import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

class FiveStoneWinningReferee : WinningReferee {

    override fun checkWin(placedStones: List<Stone>, color: Color): Boolean {
        val dx = intArrayOf(1, 1, 0, -1)
        val dy = intArrayOf(0, 1, 1, 1)

        for (x in 1..N) {
            for (y in 1..N) {
                if (placedStones.contains(Stone(Point(x, y), color)).not()) continue

                for (movingDirection in 0 until 4) {
                    var cnt = 1
                    var nx = x + dx[movingDirection]
                    var ny = y + dy[movingDirection]

                    while (nx in 1..N && ny in 1..15 && placedStones.contains(Stone(Point(nx, ny), color))) {
                        cnt++
                        nx += dx[movingDirection]
                        ny += dy[movingDirection]

                        if (cnt >= 5) return true
                    }
                }
            }
        }
        return false
    }

    companion object {
        private const val N = 15
    }
}
