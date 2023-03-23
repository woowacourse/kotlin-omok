package omok

import omok.controller.OmokController

fun main() {
    OmokController().apply {
        init()
        play()
    }
}
