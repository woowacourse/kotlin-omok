package domain.judgement

import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class FiveStoneWinningConditionChecker : WinningConditionChecker {
    override fun isWin(placedStones: List<Stone>, newStone: Stone): Boolean {
        return checkWin(placedStones.toList() + newStone, newStone.color)
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

                    while (nx in 0 until N && ny in 0 until N && placedStones.contains(
                            Stone(
                                    Position(nx, ny),
                                    color
                                )
                        )
                    ) {
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

    companion object {
        private const val N = 15
    }
}
