package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition
import woowacourse.omok.domain.model.rule.RuleAdapter

abstract class Turn(private val latestStonePosition: StonePosition) : GameState {
    abstract val stone: Stone
    abstract val rule: RuleAdapter

    abstract fun nextTurn(position: Position): GameState

    override fun place(
        board: Board,
        position: Position,
    ): GameState {
        if (alreadyStoneExist(board, position)) {
            return AlreadyHaveStone(latestStonePosition, this)
        }

        if (rule.violated(board, position)) {
            return ForbiddenPosition(latestStonePosition, this)
        }

        val stonePosition = StonePosition(position, stone)
        board.place(stonePosition)
        if (isWin(board, position)) {
            return Finished(stonePosition)
        }
        return nextTurn(position)
    }

    override fun handleInvalidPosition(handling: (StonePosition, String) -> Unit): GameState {
        throw IllegalStateException("유효한 위치였습니다.")
    }

    override fun isFinished(): Boolean = false


    private fun alreadyStoneExist(
        board: Board,
        position: Position,
    ): Boolean = board.find(position) != Stone.NONE

    private fun isWin(
        board: Board,
        position: Position,
    ): Boolean = rule.isWin(board, position)
}
