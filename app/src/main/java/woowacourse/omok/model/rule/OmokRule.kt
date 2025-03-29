package woowacourse.omok.model.rule

import omok.model.stone.position.Position
import woowacourse.omok.model.board.Board
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor

interface OmokRule {
    fun isWin(
        board: Board,
        lastStone: Stone,
    ): Boolean

    fun validate(
        board: Board,
        nextPosition: Position,
        color: StoneColor,
    ): PlacementError
}
