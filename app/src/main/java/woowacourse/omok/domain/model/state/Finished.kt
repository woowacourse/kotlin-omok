package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition

data class Finished(val latestStonePosition: StonePosition) : GameState {
    override fun place(
        board: Board,
        position: Position,
    ): GameState {
        throw IllegalStateException("게임이 종료되었습니다.")
    }

    override fun latestStone(): Stone = latestStonePosition.stone

    override fun latestPosition(): Position = latestStonePosition.position

    override fun finished(): Boolean = true

    override fun handleInvalidPosition(handling: (StonePosition, InvalidPosition) -> Unit): GameState {
        throw IllegalStateException("게임이 종료되었습니다.")
    }
}
