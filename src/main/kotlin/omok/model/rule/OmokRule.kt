package omok.model.rule

import omok.mapper.ViolationType
import omok.model.board.Board
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Position

interface OmokRule {
    fun isWin(
        board: Board,
        lastStone: Stone,
    ): Boolean

    fun validate(
        board: Board,
        nextPosition: Position,
        color: StoneColor,
    ): ViolationType
}
