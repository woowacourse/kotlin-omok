package omok.domain.model.state

import omok.domain.model.Board
import omok.domain.model.position.Position
import omok.domain.model.rule.RenjuRule
import omok.domain.model.stone.StoneType
import rule.BlackRenjuRule

class BlackStoneTurn(board: Board) : Running(board, RenjuRule(BlackRenjuRule())) {
    override val stoneType: StoneType = StoneType.BLACK

    override fun placeStone(onPlace: () -> Position): OmokState {
        return super.placeStone(StoneType.BLACK, onPlace, ::WhiteStoneTurn)
    }
}
