package woowacourse.omok.domain.state

sealed class PlaceResult {
    class Placed(
        val state: State,
    ) : PlaceResult()

    sealed class ForbiddenMove : PlaceResult() {
        object DoubleThree : ForbiddenMove()

        object DoubleFour : ForbiddenMove()

        object Overline : ForbiddenMove()

        object Occupied : ForbiddenMove()

        object OutOfBoard : ForbiddenMove()
    }
}
