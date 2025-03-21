package omok.domain.board

import omok.domain.stone.StoneColor

sealed interface BoardStatus {
    data class Moved(val color: StoneColor) : BoardStatus

    object Empty : BoardStatus

    object Blocked : BoardStatus
}
