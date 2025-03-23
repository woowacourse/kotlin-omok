package omok.mapper

sealed class ViolationType(
    message: String,
) : Exception(message)

object DoubleThreeViolation : ViolationType("3-3 반칙이 발생했습니다")

object DoubleFourViolation : ViolationType("4-4 반칙이 발생했습니다")

object OverlineViolation : ViolationType("장목 반칙이 발생했습니다")
