package omok.view.model

import omok.domain.player.Stone

fun Stone.toPresentation(): String = when (this) {
    Stone.BLACK -> "흑"
    Stone.WHITE -> "백"
}
