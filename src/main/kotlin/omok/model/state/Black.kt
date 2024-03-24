package omok.model.state

import lib.ark.ArkFourFourRule
import lib.ark.ArkOverLineRule
import lib.ark.ArkThreeThreeRule
import omok.mapper.toArkOmokBoard
import omok.mapper.toArkOmokPoint
import omok.model.Color
import omok.model.GameResult
import omok.model.Position
import omok.model.VerticalCoordinate

class Black(private val blackStatus: Array<Array<Color?>>) : TurnState(blackStatus) {
    override fun getWinningResult(
        position: Position,
        markSinglePlace: (horizontalCoordinate: Int, verticalCoordinate: Int, color: Color) -> Unit,
        addSingleStone: (Color, Position) -> Unit,
    ): GameResult? {
        if (isCurrentStoneWinner(position, Color.BLACK, markSinglePlace, addSingleStone)) {
            return GameResult.WINNER_BLACK
        }
        return null
    }

    override fun addStone(
        color: Color,
        position: Position,
        markSinglePlace: (horizontalCoordinate: Int, verticalCoordinate: Int, color: Color) -> Unit,
        addSingleStone: (Color, Position) -> Unit,
    ) {
        val arkBoard = blackStatus.toArkOmokBoard()
        val horizontalCoordinate = COMPUTATION_BOARD_SIZE - position.horizontalCoordinate.value
        val arkPoint = Position.of(horizontalCoordinate, position.verticalCoordinate.title).toArkOmokPoint()
        if (placementAvailable(arkBoard, arkPoint)) {
            val verticalCoordinate = VerticalCoordinate.valueOf(position.verticalCoordinate.title)?.value ?: return
            markSinglePlace(horizontalCoordinate, verticalCoordinate, Color.BLACK)
            addSingleStone(Color.BLACK, position)
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
