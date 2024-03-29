package woowacourse.omok.domain.model.rule.winning

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Direction
import woowacourse.omok.domain.model.Position
import kotlin.math.max

object ContinualStones {
    fun count(
        board: Board,
        position: Position,
    ): Int {
        var maxCount = 0
        Direction.biDirections().forEach { (direction1, direction2) ->
            var tempCount = 1
            tempCount += countForSingleDirection(board, position, direction1)
            tempCount += countForSingleDirection(board, position, direction2)
            maxCount = max(maxCount, tempCount)
        }
        return maxCount
    }

    private fun countForSingleDirection(
        board: Board,
        position: Position,
        direction: Direction,
    ): Int {
        val myStone = board.find(position)
        var count = 0
        var nowPos = position

        while (true) {
            nowPos = nowPos.move(direction) ?: return count
            if (board.find(nowPos) != myStone) return count
            count++
        }
    }
}
