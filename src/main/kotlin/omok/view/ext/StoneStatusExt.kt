package omok.view.ext

import omok.domain.stone.StoneColor

fun StoneColor.toLabel(): String {
    return when (this) {
        StoneColor.BLACK -> "흑"
        StoneColor.WHITE -> "백"
    }
}
