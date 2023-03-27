package woowacourse.omok

import omok.domain.player.Stone
import omok.domain.player.changeToStone

object StoneModel {
    fun String.toStone(): Stone? = when (this) {
        "흑", "BLACK" -> changeToStone(0)
        "백", "WHITE" -> changeToStone(1)
        else -> null
    }
}
