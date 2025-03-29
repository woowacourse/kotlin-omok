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
        return when (omokRule) {
            OmokRuleSet.FIVE_IN_A_ROW -> TurnResult.WIN
            OmokRuleSet.FOUR_FOUR -> throw IllegalStateException(ERROR_FOUR_FOUR)
            OmokRuleSet.THREE_THREE -> throw IllegalStateException(ERROR_THREE_THREE)
            OmokRuleSet.FULL -> TurnResult.DRAW
            else -> TurnResult.CONTINUE
        }
    }

    override fun whiteResult(
        position: Position,
        stoneState: StoneState,
    ): TurnResult {
        val omokRule = rule.validate(board, position, stoneState)
        return when (omokRule) {
            OmokRuleSet.FIVE_IN_A_ROW -> TurnResult.WIN
            OmokRuleSet.FULL -> TurnResult.DRAW
            else -> TurnResult.CONTINUE
        }
    }

    companion object {
        private const val ERROR_FOUR_FOUR = "흑은 44를 놓을 수 없습니다."
        private const val ERROR_THREE_THREE = "흑은 33을 놓을 수 없습니다."
    }
}
