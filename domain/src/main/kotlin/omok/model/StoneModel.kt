package omok.model

import omok.domain.player.Black
import omok.domain.player.Stone

fun Stone.toPresentation(): String {
    if (this == Black) return "흑"
    return "백"
}
