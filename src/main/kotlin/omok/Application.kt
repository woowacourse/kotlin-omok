package omok

import omok.controller.Controller
import omok.model.GameManager

fun main() {
    val controller = Controller(GameManager())
    controller.play()
}
