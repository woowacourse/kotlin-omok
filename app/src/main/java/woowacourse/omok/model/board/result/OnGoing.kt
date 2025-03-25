package woowacourse.omok.model.board.result

import woowacourse.omok.model.board.Point

sealed class OnGoing : PlaceStoneResult {
    data class StonePlaced(
        val point: Point,
    ) : OnGoing()

    data object RuleViolation : OnGoing()

    data object AlreadyPlaced : OnGoing()

    data object InvalidMove : OnGoing()
}
