package woowacourse.omok

import woowacourse.omok.controller.OmokController
import woowacourse.omok.view.OmokView

fun main() {
    val omokController = OmokController(OmokView())
    omokController.run()
}
