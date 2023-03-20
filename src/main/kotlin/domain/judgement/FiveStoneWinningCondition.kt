package domain.judgement

import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class FiveStoneWinningCondition : WinningCondition {
    override fun isWin(placedStones: Map<Position, Color?>, newStone: Stone): Boolean {
        val convertStones = convertMapToStones(placedStones)
        return checkWin(convertStones + newStone, newStone.color)
    }

    private fun convertMapToStones(placedStones: Map<Position, Color?>): List<Stone> {
        return placedStones.filter { it.value != null }.map {
            Stone(it.key, it.value!!)
        }
    }

    private fun checkWin(placedStones: List<Stone>, color: Color): Boolean {
        val dx = intArrayOf(1, 1, 0, -1)
        val dy = intArrayOf(0, 1, 1, 1)

        for (i in 0 until N) {
            for (j in 0 until N) {
                if (placedStones.contains(Stone(Position(i + 1, j + 1), color)).not()) continue

                for (k in 0 until 4) {
                    var cnt = 1
                    var nx = i + dx[k]
                    var ny = j + dy[k]

                    while (nx in 0 until N && ny in 0 until N && placedStones.contains(
                            Stone(Position(nx + 1, ny + 1), color)
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
