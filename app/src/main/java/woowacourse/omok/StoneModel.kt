package woowacourse.omok

import omok.domain.player.Stone

object StoneModel {
    fun String.toStone(): Stone? = when (this) {
        "흑", "BLACK" -> Stone.BLACK
        "백", "WHITE" -> Stone.WHITE
        else -> null
    }

    fun Stone.toPresentation(): String = when (this) {
        Stone.BLACK -> "흑"
        Stone.WHITE -> "백"
        else -> ""
    }
}
