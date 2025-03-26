package woowacourse.omok.domain.rule

sealed class MoveResult {
    data object Success : MoveResult()

    sealed class Failure : MoveResult() {
        data object DoubleThree : Failure()

        data object DoubleFour : Failure()

        data object OverLine : Failure()

        data object Occupied : Failure()
    }
}
