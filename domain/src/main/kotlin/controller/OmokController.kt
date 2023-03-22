package controller

import domain.game.Omok
import domain.rule.OmokRule
import view.InputView
import view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun start(blackRule: OmokRule, whiteRule: OmokRule) {
        Omok(inputView, outputView, blackRule, whiteRule).run()
    }
}
