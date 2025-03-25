package omok.model.board.result

import omok.model.board.Point

sealed class OnGoing : PlaceStoneResult {
    data class StonePlaced(val point: Point) : OnGoing()

    data object RuleViolation : OnGoing()

    data object AlreadyPlaced : OnGoing()

    data object InvalidMove : OnGoing()
}
