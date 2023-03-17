package controller

import domain.game.Omok
import domain.rule.RenjuRule
import view.InputView
import view.OutputView

class OmokController {
    fun start() {
        Omok(OutputView(), InputView(), RenjuRule()).run()
    }
}
