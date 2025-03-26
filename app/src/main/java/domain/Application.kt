package domain

import domain.controller.OmokController
import domain.view.InputView
import domain.view.OutputView

fun main() {
    val inputView = InputView()
    val outputView = OutputView()
    val controller = OmokController(inputView, outputView)
    controller.run()
}
