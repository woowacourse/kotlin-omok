package omok.model.state

import lib.ark.ArkFourFourRule
import lib.ark.ArkOverLineRule
import lib.ark.ArkThreeThreeRule
import omok.mapper.toArkOmokBoard
import omok.mapper.toArkOmokPoint
import omok.model.Color
import omok.model.GameResult
import omok.model.Position

class Black(private val blackStatus: Array<Array<Color?>>) : TurnState(blackStatus) {
    override fun getWinningResult(
        position: Position,
        markSinglePlace: (horizontalCoordinate: Int, verticalCoordinate: Int, color: Color) -> Unit,
    ): GameResult? {
        if (isCurrentStoneWinner(position, Color.BLACK, markSinglePlace)) {
            return GameResult.WINNER_BLACK
        }
        return null
    }

    override fun addStone(
        color: Color,
        position: Position,
        markSinglePlace: (horizontalCoordinate: Int, verticalCoordinate: Int, color: Color) -> Unit,
    ) {
        val arkBoard = blackStatus.toArkOmokBoard()
        val horizontalCoordinate = COMPUTATION_BOARD_SIZE - position.horizontalCoordinate.index
        val arkPoint = Position(horizontalCoordinate, position.verticalCoordinate.index).toArkOmokPoint()
        if (placementAvailable(arkBoard, arkPoint)) {
            val verticalCoordinate = position.verticalCoordinate.index
            markSinglePlace(horizontalCoordinate, verticalCoordinate, Color.BLACK)
        } else {
            throw IllegalArgumentException(EXCEPTION_FORBIDDEN_PLACEMENT)
        }
    }

    private fun placementAvailable(
        arkBoard: List<List<Int>>,
        arkPoint: Pair<Int, Int>,
    ): Boolean {
        val isNotFourFour = ArkFourFourRule.validate(arkBoard, arkPoint).not()
        val isNotThreeThree = ArkThreeThreeRule.validate(arkBoard, arkPoint).not()
        val isNotOverLine = ArkOverLineRule.validate(arkBoard, arkPoint).not()
        return isNotFourFour && isNotThreeThree && isNotOverLine
    }

    companion object {
        private const val EXCEPTION_FORBIDDEN_PLACEMENT = "금수인 위치입니다."
    }
}
