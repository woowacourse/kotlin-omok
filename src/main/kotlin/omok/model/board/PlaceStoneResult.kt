package omok.model.board

sealed class PlaceStoneResult {
    data class Success(val point: Point) : PlaceStoneResult()

    data object Closed : PlaceStoneResult()

    data object AlreadyPlaced : PlaceStoneResult()
}
