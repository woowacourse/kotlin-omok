package omok.controller

import omok.model.OmokGame
import omok.model.rule.OmokRuleJudge
import omok.model.rule.count.FiveInRowRule
import omok.view.OmokInputView
import omok.view.OmokOutputView

class OmokController(
    private val inputView: OmokInputView,
    private val outputView: OmokOutputView,
) {
    fun play() {
        val omokGameListenerImpl = OmokGameListenerImpl(inputView, outputView)
        val judge =
            OmokRuleJudge().apply {
                applyWinningRule(FiveInRowRule())
                applyRenjuRule()
            }

        OmokGame(omokGameListenerImpl).play(judge)
    }
}
