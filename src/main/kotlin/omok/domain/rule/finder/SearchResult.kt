package omok.domain.rule.finder

data class SearchResult(val stoneCount: Int, val isClosed: Boolean, val isIndirectlyClosed: Boolean = false)
