package omok.controller.ext

import omok.domain.board.StoneStatus

fun StoneStatus.toggle(): StoneStatus = if (this == StoneStatus.BLACK) StoneStatus.WHITE else StoneStatus.BLACK
