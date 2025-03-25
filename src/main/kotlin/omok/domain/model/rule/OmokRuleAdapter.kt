package omok.domain.model.rule

import omok.domain.model.Board
import omok.domain.model.stone.OmokStone
import omok.domain.model.stone.StoneType
import rule.BlackRenjuRule
import rule.WhiteRenjuRule
import rule.wrapper.point.Point

class OmokRuleAdapter : OmokRule {
    override fun checkWin(
        omokStone: OmokStone,
        board: Board,
    ): Boolean {
        return check(omokStone, board) { rule, blackPoints, whitePoints, startPoint ->
            rule.checkWin(blackPoints, whitePoints, startPoint)
        }
    }

    override fun checkAnyFoulCondition(
        omokStone: OmokStone,
        board: Board,
    ): Boolean {
        return check(omokStone, board) { rule, blackPoints, whitePoints, startPoint ->
            rule.checkAnyFoulCondition(blackPoints, whitePoints, startPoint).state.not()
        }
    }

    private fun check(
        omokStone: OmokStone,
        board: Board,
        action: (rule.OmokRule, List<Point>, List<Point>, Point) -> Boolean,
    ): Boolean {
        val omokRule = getRule(omokStone.stoneType)
        val blackPoints = board.blackStones.map { it.toPoint() }
        val whitePoints = board.whiteStones.map { it.toPoint() }
        val startPoint = omokStone.toPoint()
        return action(omokRule, blackPoints, whitePoints, startPoint)
    }

    private fun getRule(stoneType: StoneType): rule.OmokRule {
        if (stoneType == StoneType.BLACK) return BlackRenjuRule()
        return WhiteRenjuRule()
    }

    private fun OmokStone.toPoint() = Point(this.position.row.value, this.position.column.value)
}
