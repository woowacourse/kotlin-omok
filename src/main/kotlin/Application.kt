package domain

import domain.controller.Controller
import domain.domain.Board
import domain.view.OmokView

fun main() {
    val controller = Controller(Board(), OmokView())
    controller.run()
}
