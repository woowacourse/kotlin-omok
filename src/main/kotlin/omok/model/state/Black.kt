package omok.model.state

import lib.ark.ArkFourFourRule
import lib.ark.ArkOverLineRule
import lib.ark.ArkThreeThreeRule
import omok.mapper.toArkOmokBoard
import omok.mapper.toArkOmokPoint
import omok.model.Color
import omok.model.Column
import omok.model.GameResult
import omok.model.Position

class Black(private val blackStatus: Array<Array<Color?>>) : TurnState(blackStatus) {
    override fun getWinningResult(
        position: Position,
        markSinglePlace: (row: Int, col: Int, color: Color) -> Unit,
        addSingleStone: (Color, Position) -> Unit,
    ): GameResult? {
        if (isCurrentStoneWinner(position, Color.BLACK, markSinglePlace, addSingleStone)) {
            return GameResult.WINNER_BLACK
        }
        return null
    }

    override fun addStone(
        row: Int,
        col: Char,
        color: Color,
        position: Position,
        markSinglePlace: (row: Int, col: Int, color: Color) -> Unit,
        addSingleStone: (Color, Position) -> Unit,
    ) {
        val arkBoard = blackStatus.toArkOmokBoard()
        val arkPoint = Position.of(row, col).toArkOmokPoint()
        val isNotFourFour = ArkFourFourRule.validate(arkBoard, arkPoint).not()
        val isNotThreeThree = ArkThreeThreeRule.validate(arkBoard, arkPoint).not()
        val isNotJangMok = ArkOverLineRule.validate(arkBoard, arkPoint).not()
        val isPlacementAvailable = isNotFourFour && isNotThreeThree && isNotJangMok
        if (isPlacementAvailable) {
            val column = Column.valueOf(col)?.value ?: return
            markSinglePlace(row, column, Color.BLACK)
            addSingleStone(Color.BLACK, position)
        } else {
            throw IllegalArgumentException(EXCEPTION_FORBIDDEN_PLACEMENT)
        }
    }

    companion object {
        private const val EXCEPTION_FORBIDDEN_PLACEMENT = "금수인 위치입니다."
    }
}
