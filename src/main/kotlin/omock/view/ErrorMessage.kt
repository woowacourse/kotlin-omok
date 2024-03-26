package omock.view

enum class ErrorMessage(val message: String) {
    THREE_TO_THREE_COUNT_ERROR("4-4 금수를 어겼습니다."),
    FOUR_TO_FOUR_COUNT_ERROR("열린 4-4 금수를 어겼습니다."),
    IS_RESERVE_TWO_AND_THREE_ERROR("장목 금수를 어겼습니다."),
    IS_CLEAR_FOUR_TO_FOUR_COUNT_ERROR("3-3 금수를 어겼습니다."),
    ALREADY_EXIST_STONE_ERROR("이미 돌이 있습니다."),
}
