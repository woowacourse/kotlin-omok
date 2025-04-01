package woowacourse.omok.domain.board.result

import woowacourse.omok.domain.board.Point

sealed class OnGoing : PlaceStoneResult {
    data class StonePlaced(
        val point: Point,
    ) : OnGoing()

    data object RuleViolation : OnGoing()

    data object AlreadyPlaced : OnGoing()

    data object InvalidMove : OnGoing()
}
