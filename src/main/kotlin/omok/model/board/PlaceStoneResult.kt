package omok.model.board

sealed class PlaceStoneResult {
    open class Success(val point: Point) : PlaceStoneResult() {
        class Placed(point: Point) : Success(point)

        class Finished(point: Point) : Success(point)
    }

    open class Failure : PlaceStoneResult() {
        data object Closed : Failure()

        data object AlreadyPlaced : Failure()

        data object InvalidPoint : Failure()
    }
}
