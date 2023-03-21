package omok.view.model

import omok.domain.player.Black
import omok.domain.player.Stone
import omok.domain.player.White

fun Stone.toPresentation(): String = when (this) {
    Black -> "흑"
    White -> "백"
}
