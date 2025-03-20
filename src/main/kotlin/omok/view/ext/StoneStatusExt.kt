package omok.view.ext

import omok.domain.board.StoneStatus

fun StoneStatus.toLabel(): String {
    return when (this) {
        StoneStatus.BLACK -> "흑"
        StoneStatus.WHITE -> "백"
        else -> throw IllegalStateException()
    }
}
