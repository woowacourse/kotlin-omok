package woowacourse.omok.domain.fixture

import woowacourse.omok.domain.board.StoneColor
import woowacourse.omok.domain.rule.RuleValidator
import woowacourse.omok.domain.rule.count.FiveInRowRule
import woowacourse.omok.domain.rule.count.GameCountRuleAdapter
import woowacourse.omok.domain.rule.count.OverlineRule
import woowacourse.omok.domain.rule.lib.DoubleFourMoveRule
import woowacourse.omok.domain.rule.lib.DoubleThreeMoveRule
import woowacourse.omok.domain.rule.lib.ForbiddenMoveRuleAdapter

val RenjuRuleJudge =
    RuleValidator().apply {
        addWinningRule(GameCountRuleAdapter(FiveInRowRule()), listOf(StoneColor.BLACK, StoneColor.WHITE))
        addViolationRule(GameCountRuleAdapter(OverlineRule()), listOf(StoneColor.BLACK))
        addViolationRule(ForbiddenMoveRuleAdapter(DoubleThreeMoveRule()), listOf(StoneColor.BLACK))
        addViolationRule(ForbiddenMoveRuleAdapter(DoubleFourMoveRule()), listOf(StoneColor.BLACK))
    }
