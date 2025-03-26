package omok.mapper

sealed class ViolationType(
    val message: String,
)

data object DoubleThreeViolation : ViolationType("3-3 반칙이 발생했습니다")

data object DoubleFourViolation : ViolationType("4-4 반칙이 발생했습니다")

data object OverlineViolation : ViolationType("장목 반칙이 발생했습니다")

data object NoViolation : ViolationType("반칙이 발생하지 않았습니다")
