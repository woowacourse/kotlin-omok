package woowacourse.omok.controller

import omok.model.Position
import woowacourse.omok.view.OmokView

interface OmokController {
    fun onPlace(
        view: OmokView,
        position: Position,
    )
}
