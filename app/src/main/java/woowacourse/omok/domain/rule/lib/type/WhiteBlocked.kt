package woowacourse.omok.domain.rule.lib.type

enum class WhiteBlocked(
    val state: Boolean,
) {
    BLOCKED(true),
    NON_BLOCK(false), ;

    companion object {
        const val INNER_DISTANCE = 6

        fun from(isBlocked: Boolean): WhiteBlocked = entries.first { it.state == isBlocked }
    }
}
