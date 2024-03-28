package woowacourse.omok.controller

import woowacourse.omok.model.Position
import woowacourse.omok.view.OmokView

interface OmokController {
    fun onPlace(
        view: OmokView,
        position: Position,
    )
}
