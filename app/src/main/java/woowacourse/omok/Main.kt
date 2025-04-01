package omok

import omok.controller.Omok
import omok.view.OmokView

fun main() {
    val omokView = OmokView()
    Omok(omokView).play()
}
