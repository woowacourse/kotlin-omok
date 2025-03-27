package woowacourse.omok.model

sealed class MoveResult {
    sealed class Success : MoveResult() {
        data object Playing : Success()

        data class Finished(val winner: Color) : Success()
    }

    sealed class Failure : MoveResult() {
        data object PositionAlreadyOccupied : Failure()

        data object DoubleThreeViolation : Failure()

        data object DoubleFourViolation : Failure()

        data object OverlineViolation : Failure()

        data object StoneNotWithinColumn : Failure()

        data object StoneNotWithinRow : Failure()
    }
}
