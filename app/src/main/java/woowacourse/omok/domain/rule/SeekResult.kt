package woowacourse.omok.domain.rule

data class SeekResult(
    val count: Int,
    val isBlocked: Boolean,
    val isIndirectlyClosed: Boolean,
)
