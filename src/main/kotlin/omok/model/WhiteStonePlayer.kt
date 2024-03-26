package omok.model

import java.lang.IllegalStateException

class WhiteStonePlayer(override val board: Board) : Player() {
    override val color: Color = Color.WHITE

    init {
        board.addStones(stones)
    }

    override fun checkContinuity(): Boolean {
        val lastStone = stones.lastStone() ?: throw IllegalStateException("돌을 먼저 놓아주세요")
        directions.forEach { direction ->
            var count = 1
            count += countStones(lastStone.point, direction)
            count += countStones(lastStone.point, Pair(-direction.first, -direction.second))

            if (count >= 5) return true
        }
        return false
    }
}
