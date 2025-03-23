package rule.type

enum class Violation(
    val state: Boolean,
) {
    DOUBLE_THREE(true),
    DOUBLE_FOUR(true),
    OVERLINE(true),
    DUPLICATE_POSITION(true),
    NONE(false),
    ;

    fun isNone(): Boolean = this == NONE

    companion object {
        const val OVERLINE_SIZE = 6 // 6목 이상 불가능
        const val FOUL_CONDITION_SIZE = 2 // 빈칸이 2개를 탐색했다면 탐색 종료
        const val MAX_EMPTY_SIZE = 1 // 빈칸 하나는 괜찮고 더 탐색 진행
    }
}
