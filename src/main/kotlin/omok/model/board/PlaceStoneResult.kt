package omok.model.board

sealed class PlaceStoneResult {
    sealed class Success(val point: Point) : PlaceStoneResult() {
        class Placed(point: Point) : Success(point)

        class Finished(point: Point) : Success(point)
    }

    sealed class Failure : PlaceStoneResult() {
        data object Closed : Failure()

        data object AlreadyPlaced : Failure()

        data object InvalidPoint : Failure()
    }
}
