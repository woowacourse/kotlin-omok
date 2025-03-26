package omok.model.rule

import omok.model.board.Board
import omok.model.board.Position
import omok.model.omokGame.OmokRuleSet
import omok.model.stone.StoneState

interface Rule {
    fun validate(
        board: Board,
        position: Position,
        stoneState: StoneState,
    ): OmokRuleSet
}
