package woowacourse.omok.controller

import omok.model.rule.OmokRuleManager
import omok.model.rule.count.OverlineRule
import omok.model.rule.lib.DoubleFourMoveRule
import omok.model.rule.lib.DoubleThreeMoveRule
import woowacourse.omok.model.OmokGameForConsole
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.view.NextPointListener
import woowacourse.omok.view.OmokInputView
import woowacourse.omok.view.OmokOutputView

class OmokController(
    private val inputView: OmokInputView,
    private val outputView: OmokOutputView,
) {
    fun play() {
        val size = BoardSize.OMOK_BOARD_SIZE
        val rules = getRules()
        OmokGameForConsole(
            object : NextPointListener {
                override fun onNextPoint(): Pair<Int, Int> = inputView.readPosition()
            },
            outputView,
        ).play(BoardSize(size), rules)
    }

    private fun getRules(): OmokRuleManager {
        val rules = OmokRuleManager

        rules.forbiddenMoveRule.add(OverlineRule())
        rules.forbiddenMoveRule.add(DoubleThreeMoveRule())
        rules.forbiddenMoveRule.add(DoubleFourMoveRule())

        return rules
    }
}
