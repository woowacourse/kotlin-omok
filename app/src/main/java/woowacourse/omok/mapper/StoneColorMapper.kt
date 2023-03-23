package woowacourse.omok.mapper

import domain.stone.StoneColor
import woowacourse.omok.model.StoneColorModel

fun StoneColor.toPresentation(): StoneColorModel = when (this) {
    StoneColor.BLACK -> StoneColorModel.BLACK
    StoneColor.WHITE -> StoneColorModel.WHITE
}

fun StoneColorModel.toDomain(): StoneColor = when (this) {
    StoneColorModel.BLACK -> StoneColor.BLACK
    StoneColorModel.WHITE -> StoneColor.WHITE
}
