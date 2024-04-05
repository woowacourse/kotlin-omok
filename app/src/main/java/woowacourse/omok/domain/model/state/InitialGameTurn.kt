package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition

class InitialGameTurn : Running() {
    private val initialStone = Stone.BLACK

    override fun place(
        board: Board,
        position: Position,
    ): GameState {
        val stonePosition = StonePosition(position, initialStone)
        board.place(stonePosition)
        return WhiteTurn(latestStonePosition = stonePosition)
    }

    override fun latestStone(): Stone = Stone.NONE

    override fun latestPosition(): Position? = null
}
