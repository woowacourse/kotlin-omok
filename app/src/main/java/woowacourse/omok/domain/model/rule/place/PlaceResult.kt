package woowacourse.omok.domain.model.rule.place

sealed class PlaceResult {
    data object Success : PlaceResult()

    sealed class Failure : PlaceResult() {
        data object AlreadyExistStone : Failure()

        data object InvalidPosition : Failure()

        data object DoubleThreeViolation : Failure()

        data object DoubleFourViolation : Failure()

        data object OverlineViolation : Failure()
    }
}
