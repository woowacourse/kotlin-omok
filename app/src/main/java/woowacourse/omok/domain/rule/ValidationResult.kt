package woowacourse.omok.domain.rule

sealed class ValidationResult {
    data object Success : ValidationResult()

    sealed class Failure(
        open val message: String,
    ) : ValidationResult() {
        data class DoubleThree(
            override val message: String = "3x3 위치에 놓을 수 없습니다",
        ) : Failure(message)

        data class DoubleFour(
            override val message: String = "4x4 위치에 놓을 수 없습니다",
        ) : Failure(message)

        data class OverLine(
            override val message: String = "장목 위치에 놓을 수 없습니다",
        ) : Failure(message)

        data class Occupied(
            override val message: String = "이미 돌이 있습니다",
        ) : Failure(message)
    }
}
