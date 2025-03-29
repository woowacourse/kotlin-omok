package omok.model.omokGame

import omok.model.board.Board
import omok.model.board.Position
import omok.model.rule.OmokRuleAdapter
import omok.model.stone.StoneState

class OmokGameImpl(
    val board: Board,
    private val rule: OmokRuleAdapter,
) : OmokGame {
    override fun placeStone(
        position: Position,
        stoneState: StoneState,
    ) {
        board.placeStone(position, stoneState)
    }

    override fun blackResult(
        position: Position,
        stoneState: StoneState,
    ): TurnResult {
        val omokRule = rule.validate(board, position, stoneState)
        return omokRule
    }

    override fun whiteResult(
        position: Position,
        stoneState: StoneState,
    ): TurnResult {
        val omokRule = rule.validate(board, position, stoneState)
        return omokRule
    }
}
