package omok.model

sealed class MoveResult {
    sealed class Success : MoveResult() {
        data object Playing : Success()

        data object BlackWin : Success()

        data object WhiteWin : Success()
    }

    sealed class Failure : MoveResult() {
        data object PositionAlreadyOccupied : Failure()

        data object DoubleThreeViolation : Failure()

        data object DoubleFourViolation : Failure()

        data object OverlineViolation : Failure()
    }
}
