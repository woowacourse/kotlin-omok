package woowacourse.omok.fixture

import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.stone.StoneColor

val whiteStone = BoardStatus.Moved(StoneColor.WHITE)
val blackStone = BoardStatus.Moved(StoneColor.BLACK)
