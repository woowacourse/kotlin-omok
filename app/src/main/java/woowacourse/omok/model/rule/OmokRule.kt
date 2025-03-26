package omok.model.rule

import omok.model.board.Board
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Position
import woowacourse.omok.model.rule.PlacementError

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
