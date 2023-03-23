package omok.model.game

import omok.model.external.rule.BlackRenjuRule
import omok.model.external.rule.type.Violation
import omok.model.stone.Coordinate
import omok.model.stone.GoStoneColor

class BlackOmokRuleAdapter(private val board: Board) : OmokRuleAdapter {
    private val blackRenjuRule: BlackRenjuRule = BlackRenjuRule(board.sizeX, board.sizeY)

    override fun checkWin(coordinate: Coordinate): PlacementState {
        val isWin = blackRenjuRule.checkWin(
            targetPoints = board.getStonePoints(GoStoneColor.BLACK),
            otherPoints = board.getStonePoints(GoStoneColor.WHITE),
            startPoint = coordinate.toPoint(),
        )

        return if (isWin) PlacementState.WIN else PlacementState.STAY
    }

    override fun checkViolation(coordinate: Coordinate): PlacementState {
        val violation = blackRenjuRule.checkAnyFoulCondition(
            blackPoints = board.getStonePoints(GoStoneColor.BLACK),
            whitePoints = board.getStonePoints(GoStoneColor.WHITE),
            coordinate.toPoint()
        )
        return when (violation) {
            Violation.OVERLINE -> PlacementState.LONG_LINE
            Violation.DOUBLE_FOUR -> PlacementState.OPEN_DOUBLE_FOUR
            Violation.DOUBLE_THREE -> PlacementState.OPEN_DOUBLE_THREE
            Violation.NONE -> PlacementState.STAY
        }
    }
}
