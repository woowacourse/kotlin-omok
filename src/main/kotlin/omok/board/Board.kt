package omok.board

import omok.stone.Position
import omok.stone.StoneColor

class Board(val points: Points = Points.create()) {
    private var previousPoint: Point? = null

    fun placeStone(
        position: Position,
        color: StoneColor,
    ) {
        when (val result = points.placeStone(position, color)) {
            is PlaceStoneResult.Success -> {
                previousPoint = result.point
            }
            is PlaceStoneResult.Closed -> {}
            is PlaceStoneResult.AlreadyPlaced -> {}
            is PlaceStoneResult.InvalidPosition -> {}
        }
    }
}

sealed class PlaceStoneResult {
    data class Success(val point: Point) : PlaceStoneResult()

    data object Closed : PlaceStoneResult()

    data object AlreadyPlaced : PlaceStoneResult()

    data object InvalidPosition : PlaceStoneResult()
}
