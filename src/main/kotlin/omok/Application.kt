package omok

import omok.controller.Controller

fun main() {
    val controller = Controller(GameManager())
    controller.play()
}
