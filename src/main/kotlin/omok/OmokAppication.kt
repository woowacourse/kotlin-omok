package omok

import omok.controller.Controller
import omok.view.ConsoleOmokInputView

fun main() {
    Controller(
        inputView = ConsoleOmokInputView(),
    ).start()
}
