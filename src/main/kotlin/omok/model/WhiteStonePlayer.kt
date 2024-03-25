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
            count += countStones(lastStone.point, Point(-direction.row, -direction.col))

            if (count >= 5) return true
        }
        return false
    }

    companion object {
        val directions =
            arrayOf(
                Point(0, 1),
                Point(1, 0),
                Point(1, 1),
                Point(-1, 1),
            )
    }
}
