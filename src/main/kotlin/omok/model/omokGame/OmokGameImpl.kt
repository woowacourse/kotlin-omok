package omok.model.omokGame

import omok.model.board.Board
import omok.model.board.Position
import omok.model.rule.BlackWinRule
import omok.model.rule.FourFourRule
import omok.model.rule.OmokAdapter
import omok.model.rule.RulePosition
import omok.model.rule.ThreeThreeRule
import omok.model.stone.StoneState

class OmokGameImpl(
    private val board: Board,
) : OmokGame {
    override fun placeStone(
        position: Position,
        stone: StoneState,
    ) {
        board.placeStone(position, stone)
    }

    override fun blackResult(
        position: Position,
        stoneState: StoneState,
    ): TurnResult {
        val adaptedBoard = adaptedBoard(board)
        val adaptedPosition = adaptedPosition(position)
        println(BlackWinRule().validate(adaptedBoard, adaptedPosition))
        return when {
            BlackWinRule().validate(adaptedBoard, adaptedPosition) -> TurnResult.WIN
            FourFourRule().validate(adaptedBoard, adaptedPosition) -> throw IllegalStateException(ERROR_FOUR_FOUR)
            ThreeThreeRule().validate(adaptedBoard, adaptedPosition) -> throw IllegalStateException(ERROR_THREE_THREE)
            board.isFull() -> TurnResult.DRAW
            else -> TurnResult.CONTINUE
        }
    }

    override fun whiteResult(
        position: Position,
        stoneState: StoneState,
    ): TurnResult {
        val adaptedBoard = adaptedBoard(board)
        val adaptedPosition = adaptedPosition(position)
        return when {
            WhiteWinRule().validate(adaptedBoard, adaptedPosition) -> TurnResult.WIN
            board.isFull() -> TurnResult.DRAW
            else -> TurnResult.CONTINUE
        }
    }

    private fun adaptedBoard(board: Board): List<List<Int>> = OmokAdapter().adaptOmokBoard(board)

    private fun adaptedPosition(position: Position): RulePosition = OmokAdapter().adaptOmokPoint(position)

    companion object {
        private const val ERROR_FOUR_FOUR = "흑은 44를 놓을 수 없습니다."
        private const val ERROR_THREE_THREE = "흑은 33을 놓을 수 없습니다."
    }
}
