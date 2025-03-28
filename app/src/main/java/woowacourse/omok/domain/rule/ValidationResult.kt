package woowacourse.omok.domain.rule

sealed class ValidationResult {
    data object Success : ValidationResult()

    sealed class Failure() : ValidationResult() {
        data object DoubleThree : Failure()

        data object DoubleFour : Failure()

        data object OverLine : Failure()

        data object Occupied : Failure()
    }
}
