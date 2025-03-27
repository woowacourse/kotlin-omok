package woowacourse.omok.domain.board

import woowacourse.omok.domain.exception.RendjuExceptions
import woowacourse.omok.domain.stone.StoneColor

sealed interface BoardStatus {
    data class Moved(val color: StoneColor) : BoardStatus

    object Empty : BoardStatus

    data class Blocked(val cause: RendjuExceptions) : BoardStatus
}
