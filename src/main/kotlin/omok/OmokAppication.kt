package omok

import omok.controller.Controller
import omok.view.ConsoleOmokInputView
import omok.view.ConsoleOmokOutputView

fun main() {
    Controller(
        inputView = ConsoleOmokInputView(),
        outputView = ConsoleOmokOutputView(),
    ).start()
}
