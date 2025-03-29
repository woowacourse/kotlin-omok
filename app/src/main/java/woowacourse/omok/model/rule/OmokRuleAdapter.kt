package omok.model.rule

import omok.library.BlackWinRule
import omok.library.FourFourRule
import omok.library.ThreeThreeRule
import omok.library.WhiteWinRule
import omok.model.board.Board
import omok.model.board.Position
import omok.model.omokGame.TurnResult
import omok.model.stone.StoneState

class OmokRuleAdapter {
    fun validate(
        board: Board,
        position: Position,
        stoneState: StoneState,
    ): TurnResult =
        when (stoneState) {
            StoneState.BLACK -> blackStoneState(board, position)
            StoneState.WHITE -> whiteStoneState(board, position)
            else -> throw IllegalStateException("이 상황에서는 값을 판단할 수 없습니다.")
        }

    private fun blackStoneState(
        board: Board,
        position: Position,
    ): TurnResult {
        val adaptedBoard: List<List<Int>> = board.toMatrix()
        val adaptedPosition: Pair<Int, Int> = position.toCoordinates()
        return when {
            BlackWinRule.validate(adaptedBoard, adaptedPosition) -> TurnResult.WIN
            FourFourRule.validate(adaptedBoard, adaptedPosition) -> throw IllegalStateException(
                ERROR_FOUR_FOUR
            )
            ThreeThreeRule.validate(adaptedBoard, adaptedPosition) ->  throw IllegalStateException(
                ERROR_THREE_THREE
            )
            else -> TurnResult.CONTINUE
        }
    }

    private fun whiteStoneState(
        board: Board,
        position: Position,
    ): TurnResult {
        val adaptedBoard: List<List<Int>> = board.toMatrix()
        val adaptedPosition: Pair<Int, Int> = position.toCoordinates()
        if (WhiteWinRule.validate(adaptedBoard, adaptedPosition)) {
            return TurnResult.WIN
        }
        return TurnResult.CONTINUE
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
        private const val ERROR_FOUR_FOUR = "흑은 44를 놓을 수 없습니다."
        private const val ERROR_THREE_THREE = "흑은 33을 놓을 수 없습니다."
    }
}
