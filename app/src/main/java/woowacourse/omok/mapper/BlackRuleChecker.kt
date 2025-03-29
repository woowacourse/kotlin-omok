package woowacourse.omok.mapper

import omok.model.stone.position.Position
import rule.BlackRenjuRule
import rule.type.Violation
import woowacourse.omok.model.rule.PlacementError
import woowacourse.omok.model.rule.PlacementError.DoubleFourViolation
import woowacourse.omok.model.rule.PlacementError.DoubleThreeViolation
import woowacourse.omok.model.rule.PlacementError.NoViolation
import woowacourse.omok.model.rule.PlacementError.OverlineViolation

class BlackRuleChecker(
    private val rule: BlackRenjuRule,
    private val mapper: PointMapper,
) {
    fun checkFoul(
        blacks: List<Position>,
        whites: List<Position>,
        next: Position,
    ): PlacementError =
        when (
            rule.checkAnyFoulCondition(
                blacks.map { mapper.from(it) },
                whites.map { mapper.from(it) },
                mapper.from(next),
            )
        ) {
            Violation.DOUBLE_THREE -> DoubleThreeViolation
            Violation.DOUBLE_FOUR -> DoubleFourViolation
            Violation.OVERLINE -> OverlineViolation
            Violation.NONE -> NoViolation
        }

    fun checkWin(
        blacks: List<Position>,
        whites: List<Position>,
        next: Position,
    ): Boolean =
        rule.checkWin(
            blacks.map { mapper.from(it) },
            whites.map { mapper.from(it) },
            mapper.from(next),
        )
}
