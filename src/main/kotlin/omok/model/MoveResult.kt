package omok.model

sealed class MoveResult {
    sealed class Success : MoveResult() {
        data object Playing : Success()

        data object BlackWin : Success()

        data object WhiteWin : Success()
    }

    sealed class Fail : MoveResult() {
        data object StoneAlreadyPlaced : Fail()

        data object DoubleThreeViolation : Fail()

        data object DoubleFourViolation : Fail()

        data object OverlineViolation : Fail()
    }
}
