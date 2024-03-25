package core.omock.rule

import core.omock.rule.CalculateTypeErrorMessage.FOUR_TO_FOUR_COUNT_ERROR
import core.omock.rule.CalculateTypeErrorMessage.IS_CLEAR_FOUR_TO_FOUR_COUNT_ERROR
import core.omock.rule.CalculateTypeErrorMessage.IS_RESERVE_TWO_AND_THREE_ERROR
import core.omock.rule.CalculateTypeErrorMessage.THREE_TO_THREE_COUNT_ERROR

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
                is FourToFourCount -> THREE_TO_THREE_COUNT_ERROR.message
                is IsClearFourToFourCount -> FOUR_TO_FOUR_COUNT_ERROR.message
                is IsReverseTwoAndThree -> IS_RESERVE_TWO_AND_THREE_ERROR.message
                is ThreeToThreeCount -> IS_CLEAR_FOUR_TO_FOUR_COUNT_ERROR.message
            }
        }
    }
}
