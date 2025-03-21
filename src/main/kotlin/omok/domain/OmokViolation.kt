package omok.domain

enum class OmokViolation(val isError: Boolean) {
    DOUBLE_THREE(true),
    DOUBLE_FOUR(true),
    OVER_LINE(true),
    OCCUPIED(true),
    NONE(false),
}
