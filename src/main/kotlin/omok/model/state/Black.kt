package omok.model.state

import lib.ark.ArkFourFourRule
import lib.ark.ArkOverLineRule
import lib.ark.ArkThreeThreeRule
import omok.mapper.toArkOmokBoard
import omok.mapper.toArkOmokPoint
import omok.model.Board
import omok.model.Color
import omok.model.Position

class Black(private val blackStatus: Array<Array<Color>>) : TurnState() {
    override fun addStone(
        position: Position,
        placeStone: (Color, Position) -> Unit,
    ) {
        val arkBoard = blackStatus.toArkOmokBoard()
        val row = Board.ARRAY_SIZE - position.row.value
        val col = position.col.title
        val arkPoint = Position.of(row, col).toArkOmokPoint()
        if (placementAvailable(arkBoard, arkPoint)) {
            placeStone(Color.BLACK, position)
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
