package woowacourse.omok.model.position

@JvmInline
value class GridElement(
    val value: Int,
) {
    init {
        require(value >= MIN_VALUE) { "그리드 요소는 $MIN_VALUE 이상의 값만 가능합니다. value : $value" }
    }

    companion object {
        private const val MIN_VALUE = 0
    }
}
