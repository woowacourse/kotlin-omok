package woowacourse.omok.model.rule.finish

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Direction
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.game.FinishType
import woowacourse.omok.model.player.Player

class FiveStonesFinishCondition : FinishCondition {
    override fun finishType(
        board: Board,
        position: Position,
        player: Player,
    ): FinishType {
        Direction.biDirections().forEach { (direction1, direction2) ->
            var count = 1
            count += continualCount(board, position, direction1)
            count += continualCount(board, position, direction2)

            if (count == WINNING_STONE_COUNT) return FinishType.winning(board.find(position))
        }
        return FinishType.NOT_FINISH
    }

    private fun continualCount(
        board: Board,
        position: Position,
        direction: Direction,
    ): Int {
        var count = 0
        val stone = runCatching { board.find(position) }.getOrNull() ?: return count
        var nowPosition = position

        while (true) {
            nowPosition = nowPosition.move(direction)
            val nowStone = runCatching { board.find(nowPosition) }.getOrNull() ?: return count
            if (nowStone == Stone.NONE || nowStone != stone) return count
            count++
        }
    }

    companion object {
        private const val WINNING_STONE_COUNT = 5
    }
}
