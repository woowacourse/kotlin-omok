package omock.model

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
                FourToFourCount -> FOUR_TO_FOUR_COUNT_MESSAGE
                IsClearFourToFourCount -> IS_CLEAR_FOUR_TO_FOUR_COUNT_MESSAGE
                IsReverseTwoAndThree -> IS_REVERSE_TWO_AND_THREE_MESSAGE
                ThreeToThreeCount -> THREE_TO_THREE_COUNT_MESSAGE
            }
        }

        private const val FOUR_TO_FOUR_COUNT_MESSAGE = "4-4 금수를 어겼습니다."
        private const val IS_CLEAR_FOUR_TO_FOUR_COUNT_MESSAGE = "열린 4-4 금수를 어겼습니다."
        private const val IS_REVERSE_TWO_AND_THREE_MESSAGE = "장목 금수를 어겼습니다."
        private const val THREE_TO_THREE_COUNT_MESSAGE = "3-3 금수를 어겼습니다."
    }
}
