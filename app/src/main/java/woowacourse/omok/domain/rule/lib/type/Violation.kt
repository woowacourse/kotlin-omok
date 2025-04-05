package woowacourse.omok.domain.rule.lib.type

enum class Violation(
    val state: Boolean,
    val message: String,
) {
    DOUBLE_THREE(true, "현재 위치는 3-3 금수 위치입니다."),
    DOUBLE_FOUR(true, "현재 위치는 4-4 금수 위치입니다."),
    OVERLINE(true, "현재 위치는 6목 금수 위치입니다."),
    DUPLICATE_POSITION(true, "현재 위치에는 돌이 존재합니다."),
    NONE(false, "위반 사항 없습니다."),
    ;

    fun isNone(): Boolean = this == NONE

    companion object {
        const val OVERLINE_SIZE = 6 // 6목 이상 불가능
        const val FOUL_CONDITION_SIZE = 2 // 빈칸이 2개를 탐색했다면 탐색 종료
        const val MAX_EMPTY_SIZE = 1 // 빈칸 하나는 괜찮고 더 탐색 진행
    }
}
