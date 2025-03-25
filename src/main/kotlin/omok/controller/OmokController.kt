package omok.controller

import omok.model.OmokGame
import omok.model.board.BoardSize
import omok.model.board.StoneColor
import omok.model.rule.RuleValidator
import omok.model.rule.count.FiveInRowRule
import omok.model.rule.count.GameCountRuleAdapter
import omok.model.rule.count.OverlineRule
import omok.model.rule.lib.DoubleFourMoveRule
import omok.model.rule.lib.DoubleThreeMoveRule
import omok.model.rule.lib.ForbiddenMoveRuleAdapter
import omok.view.OmokInputView
import omok.view.OmokOutputView

class OmokController(
    private val inputView: OmokInputView,
    private val outputView: OmokOutputView,
) {
    fun play() {
        val omokGameListener = OmokGameHandlerImpl(inputView, outputView)
        val boardSize = BoardSize()
        val rules = prepareRules()

        OmokGame(omokGameListener).play(boardSize, rules)
    }

    private fun prepareRules(): RuleValidator {
        return RuleValidator().apply {
            applyWinningRules()
            applyViolationRules()
        }
    }

    private fun RuleValidator.applyWinningRules() {
        addWinningRule(GameCountRuleAdapter(FiveInRowRule()), listOf(StoneColor.BLACK, StoneColor.WHITE))
    }

    private fun RuleValidator.applyViolationRules() {
        addViolationRule(GameCountRuleAdapter(OverlineRule()), listOf(StoneColor.BLACK))
        listOf(DoubleThreeMoveRule(), DoubleFourMoveRule())
            .map { ForbiddenMoveRuleAdapter(it) }
            .forEach { addViolationRule(it, listOf(StoneColor.BLACK)) }
    }
}
