package omok.domain.model.state

import omok.domain.model.Board
import omok.domain.model.position.Position
import omok.domain.model.rule.OmokRule
import omok.domain.model.stone.StoneType

class WhiteStoneTurn(
    board: Board,
) : Running(board, WHITE_STONE_RULE) {
    override val stoneType: StoneType = StoneType.WHITE

    override fun placeStone(onPlace: () -> Position): OmokState {
        return super.placeStone(StoneType.WHITE, onPlace, ::BlackStoneTurn)
    }

    companion object {
        private val WHITE_STONE_RULE = OmokRule { omokStone, board -> board.canPlace(omokStone.position) }
    }
}
