package woowacourse.omok.console.mapper

import domain.stone.StoneColor
import woowacourse.omok.console.model.StoneColorModel

fun StoneColor.toPresentation(): StoneColorModel = when (this) {
    StoneColor.BLACK -> StoneColorModel.BLACK
    StoneColor.WHITE -> StoneColorModel.WHITE
}
