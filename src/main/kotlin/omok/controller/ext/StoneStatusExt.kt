package omok.controller.ext

import omok.domain.board.StoneStatus

fun StoneStatus.toggle(): StoneStatus = if (this == StoneStatus.BLACK) StoneStatus.WHITE else StoneStatus.BLACK

fun StoneStatus.toKorean(): String {
    return when (this) {
        StoneStatus.BLACK -> "흑"
        StoneStatus.WHITE -> "백"
        else -> throw IllegalStateException()
    }
}
