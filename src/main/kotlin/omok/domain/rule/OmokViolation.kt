package omok.domain.rule

enum class OmokViolation(val isError: Boolean) {
    DOUBLE_THREE(true),
    DOUBLE_FOUR(true),
    OVER_LINE(true),
    OCCUPIED(true),
    OUT_OF_BOUNDS(true),
    NONE(false),
}
