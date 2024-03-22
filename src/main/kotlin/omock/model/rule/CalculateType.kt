package omock.model.rule

sealed interface CalculateType {
    data object ThreeToThreeCount : CalculateType

    data object FourToFourCount : CalculateType

    data object IsReverseTwoAndThree : CalculateType

    data object IsClearFourToFourCount : CalculateType

    companion object {
        inline fun CalculateType.checkCalculateType(action: () -> Boolean) {
            if (action()) {
                throw IllegalArgumentException(getCalculateMessage(this))
            }
        }

        fun getCalculateMessage(calculateType: CalculateType): String {
            return when (calculateType) {
                FourToFourCount -> "4-4 금수를 어겼습니다."
                IsClearFourToFourCount -> "열린 4-4 금수를 어겼습니다."
                IsReverseTwoAndThree -> "장목 금수를 어겼습니다."
                ThreeToThreeCount -> "3-3 금수를 어겼습니다."
            }
        }
    }
}
