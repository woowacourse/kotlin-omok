package omock.model.rule

enum class CalculateTypeErrorMessage(val message: String) {
    THREE_TO_THREE_COUNT_ERROR("4-4 금수를 어겼습니다."),
    FOUR_TO_FOUR_COUNT_ERROR("열린 4-4 금수를 어겼습니다."),
    IS_RESERVE_TWO_AND_THREE_ERROR("장목 금수를 어겼습니다."),
    IS_CLEAR_FOUR_TO_FOUR_COUNT_ERROR("3-3 금수를 어겼습니다."),
}
