package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition
import woowacourse.omok.domain.model.rule.RuleAdapter2

abstract class PlayerTurn(private val latestStonePosition: StonePosition) : Running() {
    abstract val stone: Stone
    abstract val rule: RuleAdapter2

    abstract fun nextTurn(position: Position): GameState

    override fun place(
        board: Board,
        position: Position,
    ): GameState {
        val stonePosition = StonePosition(position, stone)
        if (alreadyStoneExist(board, position)) {
            return AlreadyHaveStone(StonePosition(position, stone), this)
        }

        if (rule.violated(board, position)) {
            return ForbiddenPosition(StonePosition(position, stone), this)
        }

        board.place(stonePosition)

        if (isWin(board, position)) {
            return Finished(stonePosition)
        }
        return nextTurn(position)
    }

    override fun latestStone(): Stone = latestStonePosition.stone

    override fun latestPosition(): Position = latestStonePosition.position

    private fun alreadyStoneExist(
        board: Board,
        position: Position,
    ): Boolean = board.find(position) != Stone.NONE

    private fun isWin(
        board: Board,
        position: Position,
    ): Boolean = rule.isWin(board, position)
}
