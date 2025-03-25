package domain.fixture

import omok.domain.board.BoardStatus
import omok.domain.stone.StoneColor

val whiteStone = BoardStatus.Moved(StoneColor.WHITE)
val blackStone = BoardStatus.Moved(StoneColor.BLACK)
