package omok.mapper

import omok.model.stone.position.Position
import rule.BlackRenjuRule
import rule.type.Violation
import rule.wrapper.point.Point

class BlackRuleChecker(
    private val rule: BlackRenjuRule,
    private val mapper: (Position) -> Point,
) {
    fun checkFoul(
        blacks: List<Position>,
        whites: List<Position>,
        next: Position,
    ): ViolationType =
        when (rule.checkAnyFoulCondition(blacks.map(mapper), whites.map(mapper), mapper(next))) {
            Violation.DOUBLE_THREE -> DoubleThreeViolation
            Violation.DOUBLE_FOUR -> DoubleFourViolation
            Violation.OVERLINE -> OverlineViolation
            Violation.NONE -> NoViolation
        }

    fun checkWin(
        blacks: List<Position>,
        whites: List<Position>,
        next: Position,
    ): Boolean = rule.checkWin(blacks.map(mapper), whites.map(mapper), mapper(next))
}
