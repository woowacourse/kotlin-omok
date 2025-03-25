package woowacourse.omok.model.fixture

import woowacourse.omok.model.board.StoneColor
import woowacourse.omok.model.rule.RuleValidator
import woowacourse.omok.model.rule.count.FiveInRowRule
import woowacourse.omok.model.rule.count.GameCountRuleAdapter
import woowacourse.omok.model.rule.count.OverlineRule
import woowacourse.omok.model.rule.lib.DoubleFourMoveRule
import woowacourse.omok.model.rule.lib.DoubleThreeMoveRule
import woowacourse.omok.model.rule.lib.ForbiddenMoveRuleAdapter

val RenjuRuleJudge =
    RuleValidator().apply {
        addWinningRule(GameCountRuleAdapter(FiveInRowRule()), listOf(StoneColor.BLACK, StoneColor.WHITE))
        addViolationRule(GameCountRuleAdapter(OverlineRule()), listOf(StoneColor.BLACK))
        addViolationRule(ForbiddenMoveRuleAdapter(DoubleThreeMoveRule()), listOf(StoneColor.BLACK))
        addViolationRule(ForbiddenMoveRuleAdapter(DoubleFourMoveRule()), listOf(StoneColor.BLACK))
    }
