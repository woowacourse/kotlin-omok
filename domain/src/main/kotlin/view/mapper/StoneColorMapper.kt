package view.mapper

import domain.stone.StoneColor
import view.model.StoneColorModel

fun StoneColor.toPresentation(): StoneColorModel = when (this) {
    StoneColor.BLACK -> StoneColorModel.BLACK
    StoneColor.WHITE -> StoneColorModel.WHITE
}
