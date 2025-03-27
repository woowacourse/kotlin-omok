package omok.model.rule

import omok.library.BlackWinRule
import omok.library.FourFourRule
import omok.library.ThreeThreeRule
import omok.library.WhiteWinRule
import omok.model.board.Board
import omok.model.board.Position
import omok.model.omokGame.OmokRuleSet
import omok.model.stone.StoneState

class OmokRuleAdapter : Rule {
    override fun validate(
        board: Board,
        position: Position,
        stoneState: StoneState,
    ): OmokRuleSet =
        when (stoneState) {
            StoneState.BLACK -> blackStoneState(board, position)
            StoneState.WHITE -> whiteStoneState(board, position)
            else -> throw IllegalStateException("이 상황에서는 값을 판단할 수 없습니다.")
        }

    private fun blackStoneState(
        board: Board,
        position: Position,
    ): OmokRuleSet {
        val adaptedBoard: List<List<Int>> = board.toMatrix()
        val adaptedPosition: Pair<Int, Int> = position.toCoordinates()
        return when {
            BlackWinRule.validate(adaptedBoard, adaptedPosition) -> OmokRuleSet.FIVE_IN_A_ROW
            FourFourRule.validate(adaptedBoard, adaptedPosition) -> OmokRuleSet.FOUR_FOUR
            ThreeThreeRule.validate(adaptedBoard, adaptedPosition) -> OmokRuleSet.THREE_THREE
            else -> OmokRuleSet.CONTINUE
        }
    }

    private fun whiteStoneState(
        board: Board,
        position: Position,
    ): OmokRuleSet {
        val adaptedBoard: List<List<Int>> = board.toMatrix()
        val adaptedPosition: Pair<Int, Int> = position.toCoordinates()
        if (WhiteWinRule.validate(adaptedBoard, adaptedPosition)) {
            return OmokRuleSet.FIVE_IN_A_ROW
        }
        return OmokRuleSet.CONTINUE
    }

    private fun Board.toMatrix(): List<List<Int>> =
        List(BOARD_LENGTH) { y ->
            List(BOARD_LENGTH) { x ->
                val stoneState = stoneState(x + 1, y + 1)
                when (stoneState) {
                    StoneState.NONE -> 0
                    StoneState.BLACK -> 1
                    StoneState.WHITE -> 2
                }
            }
        }

    private fun Position.toCoordinates(): Pair<Int, Int> = Pair(x.value - 1, y.value - 1)

    companion object {
        private const val BOARD_LENGTH = 15
    }
}
