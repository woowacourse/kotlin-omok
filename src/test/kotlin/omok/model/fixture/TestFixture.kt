package omok.model.fixture

import omok.model.board.StoneColor
import omok.model.rule.RuleValidator
import omok.model.rule.count.FiveInRowRule
import omok.model.rule.count.GameCountRuleAdapter
import omok.model.rule.count.OverlineRule
import omok.model.rule.lib.DoubleFourMoveRule
import omok.model.rule.lib.DoubleThreeMoveRule
import omok.model.rule.lib.ForbiddenMoveRuleAdapter

val RenjuRuleJudge =
    RuleValidator().apply {
        addWinningRule(GameCountRuleAdapter(FiveInRowRule()), listOf(StoneColor.BLACK, StoneColor.WHITE))
        addViolationRule(GameCountRuleAdapter(OverlineRule()), listOf(StoneColor.BLACK))
        addViolationRule(ForbiddenMoveRuleAdapter(DoubleThreeMoveRule()), listOf(StoneColor.BLACK))
        addViolationRule(ForbiddenMoveRuleAdapter(DoubleFourMoveRule()), listOf(StoneColor.BLACK))
    }
